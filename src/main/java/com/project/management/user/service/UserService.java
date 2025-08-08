package com.project.management.user.service;

import com.project.management.auth.dto.AuthModule;
import com.project.management.auth.dto.Permission;
import com.project.management.common.object.SearchByCriteria;
import com.project.management.user.dto.*;
import com.project.management.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * Obtiene una página del usuario basada en el criterio de búsqueda proporcionado.
     *
     * @param userId El identificador del usuario que se desea buscar.
     * @return Una página del usuario que cumplen con los criterios de búsqueda.
     */
    UserResponse getUserId(UUID userId);

    /**
     * Actualiza la información de un usuario
     *
     * @param request Los datos de actualización del usuario.
     * @param userId  El identificador del usuario a actualizar.
     */
    void updateCustomer(CustomerUpdateRequest request, UUID userId);

    /**
     * Inicia el proceso de recuperación de contraseña para un usuario.
     *
     * @param forgotPasswordRequest Los datos para iniciar el proceso de recuperación de contraseña.
     */
    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    /**
     * Verifica un token de restablecimiento de contraseña.
     *
     * @param request Los datos para verificar el token de restablecimiento de contraseña.
     */
    void verifyTokenResetPassword(ResetPasswordRequest request);

    /**
     * Obtiene un usuario por su identificador único.
     *
     * @param userId El identificador único del usuario.
     * @return El usuario encontrado o nulo si no se encuentra.
     */
    User getUserById(UUID userId);

    /**
     * Verifica si existe un usuario con el nombre de usuario especificado.
     *
     * @param userName El nombre de usuario a verificar.
     * @return `true` si existe un usuario con ese nombre de usuario, `false` en caso contrario.
     */
    Boolean existsByName(String userName);

    /**
     * Change password by user
     *
     */
    void changePassword(ChangePasswordRequest request);

    void deactivateAccountCustomer(UUID userId);

    /**
     * Actualiza si el mfa se va hacer por email o no
     *
     */
    void updateDoubleFactor(DoubleFactorAuthenticationRequest request);

    /**
     * Lista el secretKey, mfa y el qr para la autenticación.
     *
     * @return Objeto con los datos.
     */
    UserResponseDto getUser();

    /**
     * Actualiza el secretKey y el qr para la autenticación por google
     *
     * @return Objeto con los datos actualizados
     */
    UserResponseDto updateAuthenticator();

    void updatePasswordByAdmin(UUID commerceId);

    void registerNewUserAuthorizedByAdmin(UserAuthorizedDto request);

    UserAuthorized getUsersAuthorizedByAdmin(SearchByCriteria search, String quickSearch);

    UserAuthorizedDto getUserAuthorizedByUserId(UUID userId);

    void updateUserAuthorized(UserAuthorizedUpdateDto request, UUID userId);

    List<AuthModule> findModulesByUser(UUID userId);

    List<Permission> findPermissionsByUserName(String userName);

    void saveUser(User user);

    List<UserTechnical> getAllUsersByRole();
}
