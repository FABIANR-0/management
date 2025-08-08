package com.project.management.user.dto;

import com.project.management.user.status.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase que representa un DTO (Data Transfer Object) para usuarios.
 */
public final class UserDTO {

    // Identificador único del usuario
    @JsonProperty("user_id")
    private final UUID userId;

    // Indicador de administrador
    @JsonProperty("user_administrator")
    private final Boolean administrator;

    // Código de verificación
    @JsonProperty("user_code_verification")
    private final String codeVerification;

    // Fecha y hora de creación del código de verificación
    @JsonProperty("user_code_verification_created_at")
    private final LocalDateTime codeVerificationCreatedAt;

    // Fecha y hora de creación del usuario
    @JsonProperty("user_created_at")
    private final LocalDateTime createdAt;

    // Indicador si es padre o no
    @JsonProperty("user_is_parent")
    private final Boolean isParent;

    // Fecha de último inicio de sesión
    @JsonProperty("user_last_login")
    private final LocalDate lastLogin;

    // Fecha y hora de la última actualización de contraseña
    @JsonProperty("user_last_password_updated_at")
    private final LocalDateTime lastPasswordUpdatedAt;

    // Fecha y hora de bloqueo
    @JsonProperty("user_lock_date")
    private final LocalDateTime lockDate;

    // Método de inicio de sesión utilizado
    @JsonProperty("user_logged_with")
    private final String loggedWith;

    // Intentos de inicio de sesión
    @JsonProperty("user_login_attempts")
    private final Integer loginAttempts;

    // Intentos de inicio de sesión con autenticación multifactor (MFA)
    @JsonProperty("user_login_attempts_mfa")
    private final Integer loginAttemptsMfa;

    // Indicador si la autenticación multifactor (MFA) es a través de correo electrónico
    @JsonProperty("user_mfa_is_email")
    private final Boolean mfaIsEmail;

    // Nombre del usuario
    @JsonProperty("user_name")
    private final String name;

    // Contraseña del usuario (¡Nota: No es recomendable enviarla en DTOs!)
    @JsonProperty("user_password")
    private final String password;

    // Imagen de perfil del usuario
    @JsonProperty("user_profile_image")
    private final String profileImage;

    // Código autenticador de autenticación de dos factores (2FA)
    @JsonProperty("user_qr_authenticator")
    private final String qrAuthenticator;

    // Clave secreta para autenticación
    @JsonProperty("user_secret_key")
    private final String secretKey;

    // Estado del usuario (activo, inactivo, etc.)
    @JsonProperty("user_status")
    private final UserStatus status;

    // Fecha y hora de última actualización de usuario
    @JsonProperty("user_updated_at")
    private final LocalDateTime updatedAt;

    // Identificador del comercio relacionado (si aplica)
    @JsonProperty("commerce_id")
    private final UUID commerceId;

    /**
     * Constructor por defecto que inicializa todos los campos como nulos.
     */
    public UserDTO() {
        this.userId = null;
        this.administrator = null;
        this.codeVerification = null;
        this.codeVerificationCreatedAt = null;
        this.createdAt = null;
        this.isParent = null;
        this.lastLogin = null;
        this.lastPasswordUpdatedAt = null;
        this.lockDate = null;
        this.loggedWith = null;
        this.loginAttempts = null;
        this.loginAttemptsMfa = null;
        this.mfaIsEmail = null;
        this.name = null;
        this.password = null;
        this.profileImage = null;
        this.qrAuthenticator = null;
        this.secretKey = null;
        this.status = null;
        this.updatedAt = null;
        this.commerceId = null;
    }

    /**
     * Constructor con parámetros para crear un DTO a partir de un objeto User.
     * @param userId Identificador único del usuario.
     * @param administrator Indicador de administrador.
     * @param codeVerification Código de verificación.
     * @param codeVerificationCreatedAt Fecha y hora de creación del código de verificación.
     * @param createdAt Fecha y hora de creación del usuario.
     * @param isParent Indicador si es padre o no.
     * @param lastLogin Fecha de último inicio de sesión.
     * @param lastPasswordUpdatedAt Fecha y hora de la última actualización de contraseña.
     * @param lockDate Fecha y hora de bloqueo.
     * @param loggedWith Método de inicio de sesión utilizado.
     * @param loginAttempts Intentos de inicio de sesión.
     * @param loginAttemptsMfa Intentos de inicio de sesión con autenticación multifactor (MFA).
     * @param mfaIsEmail Indicador si la autenticación MFA es a través de correo electrónico.
     * @param name Nombre del usuario.
     * @param password Contraseña del usuario.
     * @param profileImage Imagen de perfil del usuario.
     * @param qrAuthenticator Código autenticador de autenticación de dos factores (2FA).
     * @param secretKey Clave secreta para autenticación.
     * @param status Estado del usuario.
     * @param updatedAt Fecha y hora de última actualización de usuario.
     * @param commerceId Identificador del comercio relacionado.
     */
    public UserDTO(UUID userId, Boolean administrator, String codeVerification, LocalDateTime codeVerificationCreatedAt, LocalDateTime createdAt, Boolean isParent, LocalDate lastLogin, LocalDateTime lastPasswordUpdatedAt, LocalDateTime lockDate, String loggedWith, Integer loginAttempts, Integer loginAttemptsMfa, Boolean mfaIsEmail, String name, String password, String profileImage, String qrAuthenticator, String secretKey, UserStatus status, LocalDateTime updatedAt, UUID commerceId) {
        this.userId = userId;
        this.administrator = administrator;
        this.codeVerification = codeVerification;
        this.codeVerificationCreatedAt = codeVerificationCreatedAt;
        this.createdAt = createdAt;
        this.isParent = isParent;
        this.lastLogin = lastLogin;
        this.lastPasswordUpdatedAt = lastPasswordUpdatedAt;
        this.lockDate = lockDate;
        this.loggedWith = loggedWith;
        this.loginAttempts = loginAttempts;
        this.loginAttemptsMfa = loginAttemptsMfa;
        this.mfaIsEmail = mfaIsEmail;
        this.name = name;
        this.password = password;
        this.profileImage = profileImage;
        this.qrAuthenticator = qrAuthenticator;
        this.secretKey = secretKey;
        this.status = status;
        this.updatedAt = updatedAt;
        this.commerceId = commerceId;
    }

    /**
     * Método estático para convertir un objeto User a un UserDTO.
//     * @param user Objeto User a convertir.
     * @return UserDTO creado a partir del objeto User.
     */
//    public static UserDTO fromUserTableRow(User user) {
//        UUID commerceId = (user.getCommerce() != null && user.getCommerce().getCommerceId() != null) ?
//                user.getCommerce().getCommerceId() : UUID.fromString("00000000-0000-0000-0000-000000000000");
//
//        return new UserDTO(
//                user.getUserId(),
//                user.isAdministrator(),
//                user.getCodeVerification(),
//                user.getCodeVerificationCreatedAt(),
//                user.getCreatedAt(),
//                user.isParent(),
//                user.getLastLogin(),
//                user.getLastPasswordUpdatedAt(),
//                user.getLockDate(),
//                user.getLoggedWith(),
//                user.getLoginAttempts(),
//                user.getLoginAttemptsMfa(),
//                user.isMfaIsEmail(),
//                user.getName(),
//                user.getPassword(),
//                user.getProfileImage(),
//                user.getQrAuthenticator(),
//                user.getSecretKey(),
//                user.getStatus(),
//                user.getUpdatedAt(),
//                commerceId
//        );
//    }

    // Métodos para obtener valores específicos del DTO (Getters)
    public UUID userId() {
        return userId;
    }

    public Boolean administrator() {
        return administrator;
    }

    public String codeVerification() {
        return codeVerification;
    }

    public LocalDateTime codeVerificationCreatedAt() {
        return codeVerificationCreatedAt;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public Boolean isParent() {
        return isParent;
    }

    public LocalDate lastLogin() {
        return lastLogin;
    }

    public LocalDateTime lastPasswordUpdatedAt() {
        return lastPasswordUpdatedAt;
    }

    public Integer loginAttempts() {
        return loginAttempts;
    }

    public Integer loginAttemptsMfa() {
        return loginAttemptsMfa;
    }

    public Boolean mfaIsEmail() {
        return mfaIsEmail;
    }

    public String name() {
        return name;
    }

    public String password() {
        return password;
    }

    public String profileImage() {
        return profileImage;
    }

    public String qrAuthenticator() {
        return qrAuthenticator;
    }

    public String secretKey() {
        return secretKey;
    }

    public UserStatus status() {
        return status;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    public UUID commerceId() {
        return commerceId;
    }
}
