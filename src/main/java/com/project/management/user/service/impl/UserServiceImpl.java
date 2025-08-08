package com.project.management.user.service.impl;

import com.project.management.auth.dto.AuthModule;
import com.project.management.auth.dto.Permission;
import com.project.management.auth.exception.AuthenticationFailedException;
import com.project.management.auth.service.AuthService;
import com.project.management.common.criteria.Criteria;
import com.project.management.common.criteria.Filters;
import com.project.management.common.criteria.Order;
import com.project.management.common.encryption.Encryption;
import com.project.management.common.exception_handler.ResourceNotFoundException;
import com.project.management.common.object.SearchByCriteria;
import com.project.management.common.parse.ParseFilters;
import com.project.management.common.qr.QrUtils;
import com.project.management.common.util.UtilString;
import com.project.management.email.service.EmailUserService;
import com.project.management.role.entity.Role;
import com.project.management.role.service.RoleService;
import com.project.management.user.dto.*;
import com.project.management.user.entity.User;
import com.project.management.user.repository.UserRepository;
import com.project.management.user.service.PasswordHistoryService;
import com.project.management.user.service.PersonService;
import com.project.management.user.service.UserService;
import com.project.management.user.status.UserStatus;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Clase que proporciona servicios relacionados con la gestión de usuarios.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonService personService;
    private final AuthService authService;
    private final Encryption encryption;
    private final EmailUserService emailUserService;
    private final PasswordHistoryService passwordHistoryService;
    private final RoleService roleService;
    private final QrUtils qrUtils;

    @Value("${settings.url.front}")
    private String urlFrontEnd;

    /**
     * Constructor para inicializar el servicio de usuario con todas las dependencias necesarias.
     *
     * @param userRepository         El repositorio de usuarios para gestionar entidades de usuario.
     * @param passwordEncoder        El codificador de contraseñas para garantizar la seguridad de las contraseñas almacenadas.
     * @param personService          El servicio de persona para gestionar la información personal de los usuarios.
     * @param encryption             El servicio de cifrado para operaciones de seguridad.
     * @param passwordHistoryService El servicio de historial de contraseñas para realizar un seguimiento de las contraseñas anteriores.
     */
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, PersonService personService, AuthService authService, @Qualifier("EncryptionAES") Encryption encryption, EmailUserService emailUserService, PasswordHistoryService passwordHistoryService, RoleService roleService, QrUtils qrUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.personService = personService;
        this.authService = authService;
        this.encryption = encryption;
        this.emailUserService = emailUserService;
        this.passwordHistoryService = passwordHistoryService;
        this.roleService = roleService;
        this.qrUtils = qrUtils;
    }

    @Override
    public UserResponse getUserId(UUID userId) {
        return userRepository.getUserId(userId);
    }

    private User userAuth() {
        User user = authService.getUserAuthenticated();
        if (user == null)
            throw new AuthenticationFailedException("No hay una sesión activa");
        return user;
    }

    @Override
    public Boolean existsByName(String userName) {
        return userRepository.existsByName(userName.toLowerCase(Locale.ROOT));
    }

    @Transactional
    @Override
    public void updatePasswordByAdmin(UUID userId) {
        User userAuth = authService.getUserAuthenticated();
        if (userAuth == null || !userAuth.isAdministrator()) {
            throw new AuthenticationFailedException("No hay una sesión activa y solo el administrador tiene estos permisos");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String password = UtilString.generateRandom(12);
        this.userReset(user, password);
        emailUserService.resetUserPassword(user, password);
    }

    @Transactional
    @Override
    public void registerNewUserAuthorizedByAdmin(UserAuthorizedDto request) {
        User userAuth = this.userAuth();
        List<UUID> rolesId = request.getRoles();
        String secretKey = UtilString.generateSecretKey();
        String randomPassword = UtilString.generateRandom(12);
        String name = request.getName().toLowerCase(Locale.ROOT);
        User user = User.create(
                name.toLowerCase(Locale.ROOT),
                passwordEncoder.encode(randomPassword),
                secretKey,
                qrUtils.createQrAuthenticator(secretKey, name, "wposs"),
                false,
                null
        );
        user = userRepository.save(user);

        passwordHistoryService.addPasswordHistory(randomPassword, user);
        roleService.assignRole(rolesId, user.getUserId());
        personService.createPersonAuthorized(user, request.getPerson());
        emailUserService.sendUserCredentials(request, userAuth, randomPassword, "Laboratory");
    }

    @Override
    public UserAuthorized getUsersAuthorizedByAdmin(SearchByCriteria search, String quickSearch) {
        User user = this.userAuth();
        Order order = Order.fromValues(search.orderBy(), search.orderType());

        if (!order.hasOrder()) {
            order = Order.desc("created_at");
        }
        Criteria criteria = new Criteria(new Filters(ParseFilters.getFilters(search.filters())), order, search.limit(), search.offset());
        return new UserAuthorized(userRepository.countUserAuthorized(), userRepository.getUsersAuthorizedByAdmin(criteria, quickSearch));
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByNameAndPersonEmail(request.getUserName().toLowerCase(Locale.ROOT).replace(" ", ""), request.getEmail().toLowerCase(Locale.ROOT)).orElseThrow(() -> new ResourceNotFoundException("Usuario No Encontrado"));
        user.resetLastPasswordUpdatedAt();
        userRepository.save(user);

        String json = "{'user_id':'" + user.getUserId() + "','user_name':'" + user.getName() + "', 'timestamp':'" + LocalDateTime.now().plusDays(1) + "'}";
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        String token = encryption.encrypt(jsonObject.toString(), null);
        String urlToken = urlFrontEnd + "/auth/reset_password/" + token;

        emailUserService.recoveryPasswordEmailSent(user, urlToken);
    }

    public void userReset(User user, String password) {
        user.resetPassword(
                passwordEncoder.encode(password),
                UserStatus.ACTIVE,
                LocalDateTime.now(),
                0,
                0
        );
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void verifyTokenResetPassword(ResetPasswordRequest request) {
        String password = request.getPassword();

        /* Desencriptamos los datos del token y obtenemos el JsonObject */
        JsonObject token = new Gson().fromJson(encryption.decrypt(request.getToken(), null), JsonObject.class);

        /* Se extraen los datos del token */
        UUID userId = UUID.fromString(token.get("user_id").getAsString());
        String nameUser = token.get("user_name").getAsString();
        LocalDateTime expirationDate = LocalDateTime.parse(token.get("timestamp").getAsString());

        User user = userRepository.findByUserIdAndName(userId, nameUser).orElseThrow(() -> new ResourceNotFoundException("Usuario No Encontrado"));

        /* Se valida los campos enviados en la petición */
        if (user.getLastPasswordUpdatedAt() != null) {
            throw new IllegalArgumentException("El token ya fue utilizado");
        }

        if (LocalDateTime.now().isAfter(expirationDate)) {
            throw new IllegalArgumentException("El token ya ha expirado");
        }

        /* Actualizamos la contraseña*/
        this.updatePassword(user, password);

        this.userReset(user, password);

        emailUserService.changePassword(user);
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = this.userAuth();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual que has ingresado no es correcta, por favor vuelve a intentarlo");
        }

        /* Actualizamos la contraseña*/
        updatePassword(user, request.getPassword());
        emailUserService.changePassword(user);
    }

    @Override
    public void deactivateAccountCustomer(UUID userId) {
        User user = this.getUserById(userId);
        user.deactivateAccount(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    @Override
    public void updateDoubleFactor(DoubleFactorAuthenticationRequest request) {
        User user = this.userAuth();
        user.updateMfaIsEmail(request.getUserMfaEmail());
        userRepository.save(user);
    }

    /**
     * Actualizar la contraseña de un usuario
     *
     * @param user     Usuario al que se editara la contraseña
     * @param password Contraseña a actualizar
     */
    private void updatePassword(User user, String password) {
        if (Boolean.TRUE.equals(passwordHistoryService.validateLastPassword(user, password))) {
            throw new IllegalArgumentException("Esta contraseña ha sido utilizada anteriormente, intenta con una nueva");
        }
        /* Se guarda la nueva contraseña y se actualiza los datos referentes a la última actualización de contraseña **/
        user.updatePassword(passwordEncoder.encode(password));
        user.resetLastPasswordAndPasswordHistoryUpdate();
        userRepository.save(user);
        passwordHistoryService.addPasswordHistory(password, user);
    }

    public UserResponseDto getUser() {
        UUID userId = this.userAuth().getUserId();
        Optional<User> user = this.userRepository.findById(userId);
        return user.map(value -> UserResponseDto.create(value.isMfaIsEmail(), value.getSecretKey(), value.getQrAuthenticator()
        )).orElse(null);
    }

    @Override
    public UserResponseDto updateAuthenticator() {
        User user = this.userAuth();
        String secretKey = UtilString.generateSecretKey();
        String qrAuthenticator = qrUtils.createQrAuthenticator(secretKey, user.getName(), "Guayaquil");
        user.updateAuthenticator(secretKey, qrAuthenticator);
        userRepository.save(user);
        return UserResponseDto.create(user.isMfaIsEmail(), secretKey, qrAuthenticator);
    }

    @Override
    public void updateCustomer(CustomerUpdateRequest request, UUID userId) {
        User user = this.getUserById(userId);
        user.updateImageAndUserName(request.getUserName().toLowerCase(Locale.ROOT).replace(" ", ""), request.getImageProfile());
        personService.updatePerson(request.getPerson(), userRepository.save(user));
    }

    @Override
    @Transactional
    public UserAuthorizedDto getUserAuthorizedByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        List<UUID> roles = user.getRoles().stream().map(Role::getRoleId).filter(roleId -> !roleId.equals(UUID.fromString("a06587d6-3f5f-4859-addf-22cb1a29a786"))).collect(Collectors.toList());
        PersonCreateDto person = PersonCreateDto.create(
                user.getPerson().getName(),
                user.getPerson().getLastname(),
                user.getPerson().getPhone(),
                user.getPerson().getEmail(),
                user.getPerson().getCharge()
        );
        return UserAuthorizedDto.create(
                user.getName(),
                roles,
                user.getStatus().getValue(),
                person,
                user.getProfileImage()
        );
    }

    @Override
    public void updateUserAuthorized(UserAuthorizedUpdateDto request, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        personService.UpdatePersonAuthorized(request.getPerson(), user);
        user.addRole(roleService.getRolesByIds(request.getRoles()));
        user.changeStatus(UserStatus.fromValue(request.getStatus()));
        if(user.getStatus().hasActive() && user.getLoginAttempts() >= 3)
            user.resetLoginAttempt(0);
        user.changeUserName(request.getName().toLowerCase(Locale.ROOT));
        userRepository.save(user);
    }

    @Override
    public List<AuthModule> findModulesByUser(UUID userId) {
        return userRepository.findModulesByUser(userId);
    }

    @Override
    public List<Permission> findPermissionsByUserName(String userName) {
        return userRepository.findPermissionsByUserName(userName);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<UserTechnical> getAllUsersByRole() {
        return userRepository.getAllUsersByRole();
    }
}
