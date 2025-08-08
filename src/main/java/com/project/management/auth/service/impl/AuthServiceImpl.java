package com.project.management.auth.service.impl;

import com.project.management.auth.dto.*;
import com.project.management.auth.exception.AuthenticationFailedException;
import com.project.management.auth.security.jwt.JwtTokenProvider;
import com.project.management.auth.service.AuthService;
import com.project.management.email.service.EmailUserService;
import com.project.management.user.entity.LoginHistory;
import com.project.management.user.entity.LoginIp;
import com.project.management.user.entity.RefreshToken;
import com.project.management.user.entity.User;
import com.project.management.user.repository.LoginHistoryRepository;
import com.project.management.user.repository.LoginIpRepository;
import com.project.management.user.repository.UserRepository;
import com.project.management.user.service.RefreshTokenService;
import com.project.management.user.status.UserStatus;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

/**
 * Servicio que maneja la autenticación y los tokens de acceso en la aplicación.
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final LoginIpRepository loginIpRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailUserService emailUserService;
    private final UserDetailsService userDetailsService;
    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
    private final AuthenticationManager authenticationManager;

    /**
     * Constructor del servicio AuthService.
     *
     * @param userRepository         Repositorio para acceder a los datos de los usuarios.
     * @param loginHistoryRepository Repositorio para acceder a los registros de historial de inicio de sesión.
     * @param loginIpRepository      Repositorio para acceder a los registros de direcciones IP de inicio de sesión.
     * @param passwordEncoder        Codificador de contraseñas utilizado para cifrar y verificar contraseñas.
     * @param jwtTokenProvider       Proveedor de tokens JWT para generar y validar tokens de acceso.
     */
    public AuthServiceImpl(UserRepository userRepository, LoginHistoryRepository loginHistoryRepository, LoginIpRepository loginIpRepository, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService, JwtTokenProvider jwtTokenProvider, EmailUserService emailUserService, UserDetailsService userDetailsService1, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.loginHistoryRepository = loginHistoryRepository;
        this.loginIpRepository = loginIpRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailUserService = emailUserService;
        this.userDetailsService = userDetailsService1;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Realiza el proceso de inicio de sesión para un cliente.
     *
     * @param request La solicitud de inicio de sesión del cliente.
     * @return La respuesta de inicio de sesión del cliente.
     * @throws AuthenticationFailedException Si el inicio de sesión falla debido a credenciales inválidas.
     */
    @Override
    @Transactional(dontRollbackOn = {AuthenticationFailedException.class})
    public AuthCustomerResponse loginCustomer(LoginCustomerRequest request) {
        LocalDateTime date = LocalDateTime.now();
        /****-- Obtenemos el usuario por el nombre de usuario --***/
        User user = userRepository.getUserByName(request.getName().trim().toLowerCase(Locale.ROOT));

        /****-- Si el usuario no existe se llama una excepción de autenticación --***/
        if (user == null) {
            throw new AuthenticationFailedException("Credenciales incorrectas");
        }

        /****-- Creamos un nuevo registro para el histórico de login --***/
        LoginHistory loginHistory = LoginHistory.create(user, true);

        /****-- Verificamos si el usuario está bloqueado --***/
        if (user.getStatus().equals(UserStatus.LOCKED)) {
            loginHistory.addMessage("Usuario bloqueado");
            loginHistory.updateSuccess(false);
            loginHistoryRepository.save(loginHistory);
            throw new AuthenticationFailedException("Usuario bloqueado por intentos fallidos, para ingresar cambia tu contraseña");
        }

        /****-- Verificamos si el usuario y el comercio no están activos --***/
        if (!user.getStatus().hasActive()) {
            String message = "Usuario inactivo";
            loginHistory.addMessage(message);
            loginHistory.updateSuccess(false);
            loginHistoryRepository.save(loginHistory);
            throw new AuthenticationFailedException(message);
        }

        /****-- se realiza el proceso de intento fallido en el caso de que las contraseñas no coincidan --***/
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            /****-- Contamos el intento de logueo para guardarlo en el objeto user--****/
            if (user.getLastLogin() != null && user.getLastLogin().toLocalDate().equals(date.toLocalDate())) {
                user.addLoginAttempt(user.getLoginAttempts() + 1, date);
            } else {
                user.addLoginAttempt(1, date);
            }

            /****--Verificamos si el usuario cumplió el máximo de intentos permitidos, para luego bloquearlo y notificar al cliente. --***/
            if (user.getLoginAttempts() == 3) {
                loginHistory.addMessage("Usuario bloqueado por contraseña");
                loginHistory.updateSuccess(false);
                loginHistoryRepository.save(loginHistory);
                user.changeStatus(UserStatus.LOCKED);
                userRepository.save(user);
                throw new AuthenticationFailedException("Usuario bloqueado por intentos fallidos, para ingresar cambia tu contraseña");
            }

            /****-- Realizamos registro del histórico de intentos de logueo --****/
            loginHistory.addMessage("Credenciales incorrectas");
            loginHistory.updateSuccess(false);
            loginHistoryRepository.save(loginHistory);

            /****-- Salvamos cambios realizados el usuario --****/
            userRepository.save(user);

            throw new AuthenticationFailedException(this.getMessageAttempts(user.getLoginAttempts()));
        }

        /****-- Si las credenciales están bien, se resetea él número de intentos que puede llevar el usuario--****/
        user.resetLoginAttempt(0);
        userRepository.save(user);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName().toLowerCase(Locale.ROOT).trim(), request.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new AuthenticationFailedException("Tenemos problemas, reintente mas tarde...");
        }

        /****-- Verificamos que si el usuario tiene envío de mfa por correo, si es asi enviamos el código y validamos que este todo ok --***/
//        if (Boolean.TRUE.equals(user.isMfaIsEmail()) && Boolean.FALSE.equals(sendCodeVerification(user))) {
//            throw new AuthenticationFailedException("Tenemos problemas con el servicio de correos, reintenta mas tarde...");
//        }
//        return LoginCustomerResponse.create(user.getUserId(), user.isMfaIsEmail(), UtilString.emailConvertMask(user.getPerson().getEmail()));
        refreshTokenService.deleteByUser(user.getUserId());
        String accessToken = generateToken(user.getName());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        TokenResponse tokenResponse = TokenResponse.create(accessToken, refreshToken.getToken());
        UserCustomerResponse userCustomerResponse = getUserCustomerResponse(user);

        user.updateLastLogin(date);
        loginHistory.addMessage("Logueado correctamente");
        loginHistory.updateSuccess(true);
        loginHistoryRepository.save(loginHistory);
        userRepository.save(user);

        return AuthCustomerResponse.create(tokenResponse, userCustomerResponse, userRepository.findModulesByUser(user.getUserId()), userRepository.findPermissionsByUserName(user.getName()), verifyPasswordUser(user));
    }

    /**
     * Este método envía el correo electrónico del código de verificación al usuario
     *
     * @return Retorna si se envió el correo electrónico
     */
    private Boolean sendCodeVerification(User user) {
        SecureRandom random = new SecureRandom();
        int numberRandom = random.nextInt(999999);
        String code = String.format("%06d", numberRandom);
        user.addCodeVerification(code);
        userRepository.save(user);
        return emailUserService.sendCodeVerification(user);
    }

    @Override
    @Transactional(dontRollbackOn = {AuthenticationFailedException.class})
    public AuthCustomerResponse mfaAuthenticationCustomer(MfaRequest request) {
        LocalDateTime date = LocalDateTime.now();

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new AuthenticationFailedException("Tenemos problemas, reintenta mas tarde..."));

        /****-- Creamos un nuevo registro para el histórico de login --***/
        LoginHistory loginHistory = LoginHistory.create(user, true);

        if (Boolean.TRUE.equals(user.isMfaIsEmail())) {
            if (user.getCodeVerificationCreatedAt() == null) {
                throw new AuthenticationFailedException("El código es inválido");
            }
            Duration duration = Duration.between(user.getCodeVerificationCreatedAt(), LocalDateTime.now());
            if (duration.toMinutes() > 5) {
                throw new AuthenticationFailedException("El código ha expirado, vuelve a iniciar sesión");
            }
        }

        if ((user.isMfaIsEmail() && user.getCodeVerification().equals(request.getCode())) ||
                (!user.isMfaIsEmail() && user.getSecretKey() != null
                        && googleAuthenticator.authorize(user.getSecretKey(), Integer.parseInt(request.getCode())))) {

            user.resetCodeVerification();
            user.addLoginAttemptMfa(0);
            userRepository.save(user);
            if (request.getIpAddress() != null) {
                LoginIp loginIp = LoginIp.create(user, request.getIpAddress());
                loginIpRepository.save(loginIp);
            }

            refreshTokenService.deleteByUser(user.getUserId());
            String accessToken = generateToken(user.getName());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
            TokenResponse tokenResponse = TokenResponse.create(accessToken, refreshToken.getToken());
            UserCustomerResponse userCustomerResponse = getUserCustomerResponse(user);

            user.updateLastLogin(date);
            loginHistory.addMessage("Logueado correctamente");
            loginHistory.updateSuccess(true);
            loginHistoryRepository.save(loginHistory);
            userRepository.save(user);
            return AuthCustomerResponse.create(tokenResponse, userCustomerResponse, userRepository.findModulesByUser(user.getUserId()), userRepository.findPermissionsByUserName(user.getName()), verifyPasswordUser(user));
        } else {
            loginHistory.addMessage("Código incorrecto");
            loginHistory.updateSuccess(false);
            loginHistoryRepository.save(loginHistory);
        }

        if (user.getLoginAttemptsMfa() < 3) {
            user.updateLastLogin(date);
            if (user.getLastLogin() != null && user.getLastLogin().toLocalDate().equals(date.toLocalDate())) {
                user.addLoginAttemptMfa(user.getLoginAttemptsMfa() + 1);
            } else {
                user.addLoginAttemptMfa(1);
            }
        }

        if (user.getLoginAttemptsMfa() >= 3) {
            loginHistory.addMessage("Usuario bloqueado por mfa");
            loginHistory.updateSuccess(false);
            loginHistoryRepository.save(loginHistory);
            user.changeStatus(UserStatus.LOCKED);
            userRepository.save(user);
            throw new AuthenticationFailedException("Usuario bloqueado por intentos fallidos, para ingresar comunicate con el administrador");
        }

        userRepository.save(user);
        throw new AuthenticationFailedException(this.getMessageAttempts(user.getLoginAttemptsMfa()));
    }

    /**
     * Verifica la antigüedad de la última actualización de contraseña de un usuario.
     * Devuelve un estado que indica si la contraseña es la primera, ha expirado o ninguna de las anteriores.
     *
     * @param user El usuario del cual se verifica la contraseña.
     * @return Cadena que indica el estado de la contraseña ("First" si es la primera vez, "Expired" si ha expirado, "None" si no ha expirado).
     */
    public String verifyPasswordUser(User user) {
        // Verifica si la última actualización de contraseña es nula (primera vez que se establece la contraseña)
        if (user.getLastPasswordUpdatedAt() == null) return "First";

        // Calcula la duración desde la última actualización de contraseña hasta ahora
        Duration duration = Duration.between(user.getLastPasswordUpdatedAt(), LocalDateTime.now());

        // Verifica si han pasado más de 90 días desde la última actualización de contraseña
        if (duration.toDays() >= 90) return "Expired";

        // Si la contraseña no ha expirado
        return "None";
    }

    private static UserCustomerResponse getUserCustomerResponse(User user) {
        return UserCustomerResponse.create(
                user.getUserId(),
                user.getName(),
                user.isAdministrator(),
                !user.getRoles().isEmpty() && user.getRoles().stream().anyMatch(role -> role.getName().equals("Técnico")),
                String.valueOf(user.getStatus()),
                user.getProfileImage(),
                user.getPerson().getName(),
                user.getPerson().getLastname(),
                user.getPerson().getPhone(),
                user.getPerson().getEmail(),
                user.getPerson().getCharge()
        );
    }

    /**
     * Genera un token JWT para el nombre de usuario especificado.
     *
     * @param userName El nombre de usuario para el cual se generará el token JWT.
     * @return El token JWT generado.
     */
    @Override
    public String generateToken(String userName) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return jwtTokenProvider.generateToken(userDetails.getUsername());
    }

    /**
     * Obtiene el mensaje de intentos de inicio de sesión restantes.
     *
     * @param loginAttemptRemaining El número de intentos de inicio de sesión restantes.
     * @return El mensaje de intentos de inicio de sesión restantes.
     */
    private String getMessageAttempts(Integer loginAttemptRemaining) {
        String n = loginAttemptRemaining != 3 ? "n" : "";
        String s = loginAttemptRemaining != 3 ? "s" : "";
        return String.format("Credenciales incorrectas: Queda%s %d intento%s.", n, (3 - loginAttemptRemaining), s);
    }

    /**
     * Reenvía el proceso de autenticación de doble factor (2FA) para el usuario especificado.
     *
     * @param userId El ID del usuario para el cual se reenviará la autenticación de 2FA.
     * @throws AuthenticationFailedException sí se encuentran problemas durante el proceso de reenvío.
     */
    @Override
    public void resendTwoFactorAuthentication(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AuthenticationFailedException("Tenemos problemas, reintenta mas tarde..."));

        if (Boolean.TRUE.equals(user.isMfaIsEmail()) && Boolean.FALSE.equals(sendCodeVerification(user))) {
            throw new AuthenticationFailedException("Tenemos problemas con el servicio de correos, reintenta mas tarde...");
        }
    }

    /**
     * Obtiene el usuario autenticado en la sesión
     *
     * @return Objeto con los datos de usuario
     */
    @Override
    public User getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userRepository.getUserByName(userDetails.getUsername());
        }
        return null;
    }
}