package com.project.management.user.entity;

import com.project.management.common.util.AuditEntity;
import com.project.management.role.entity.Role;
import com.project.management.user.status.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Representa un usuario en el sistema.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user", schema = "main")
public class User extends AuditEntity {

    private static final String DEFAULT_LOGIN = "UsernameAndPassword";
    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;

    /**
     * Nombre del usuario.
     */
    @Column(name = "name", nullable = false, length = 38, unique = true)
    private String name;

    /**
     * Estado del usuario.
     */
    @Column(name = "status", nullable = false)
    private UserStatus status;

    /**
     * Indicador de si el usuario es administrador.
     */
    @Column(name = "administrator")
    private boolean administrator;

    /**
     * Contraseña del usuario.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Fecha y hora de la última actualización de la contraseña.
     */
    @Column(name = "last_password_updated_at")
    private LocalDateTime lastPasswordUpdatedAt;

    /**
     * Código de verificación del usuario.
     */
    @Column(name = "code_verification", length = 6)
    private String codeVerification;

    /**
     * Fecha y hora de creación del código de verificación.
     */
    @Column(name = "code_verification_created_at")
    private LocalDateTime codeVerificationCreatedAt;

    /**
     * Fecha de último inicio de sesión del usuario.
     */
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    /**
     * Intentos de inicio de sesión del usuario.
     */
    @Column(name = "login_attempts")
    private Integer loginAttempts;

    /**
     * Intentos de inicio de sesión MFA (Autenticación Multifactor) del usuario.
     */
    @Column(name = "login_attempts_mfa")
    private Integer loginAttemptsMfa;

    /**
     * Indicador de si la autenticación MFA se realiza a través del correo electrónico.
     */
    @Column(name = "mfa_is_email")
    private boolean mfaIsEmail;

    /**
     * Clave secreta para la autenticación MFA.
     */
    @Column(name = "secret_key")
    private String secretKey;

    /**
     * Fecha y hora de bloqueo del usuario.
     */
    @Column(name = "lock_date")
    private LocalDateTime lockDate;

    /**
     * Código QR para la autenticación MFA.
     */
    @Column(name = "qr_authenticator", columnDefinition = "TEXT")
    private String qrAuthenticator;

    /**
     * Imagen de perfil del usuario.
     */
    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "logged_with")
    private String loggedWith;

    /**
     * Lista de roles asignados al usuario.
     */
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "user_role", schema = "main",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(name = "uc_user_role", columnNames = {"user_id", "role_id"}))
    private List<Role> roles;



    /**
     * Persona relacionada con el usuario.
     */
    @OneToOne(mappedBy = "user")
    private Person person;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER,mappedBy = "user")
    private List<PasswordHistory> passwordHistories;

    public User(String name, String password, String secretKey, String qrAuthenticator,boolean administrator, String profileImage) {
        this.name = name;
        this.status = UserStatus.ACTIVE;
        this.password = password;
        this.loginAttempts = 0;
        this.loginAttemptsMfa = 0;
        this.mfaIsEmail = true;
        this.secretKey = secretKey;
        this.qrAuthenticator = qrAuthenticator;
        this.administrator = administrator;
        this.profileImage = profileImage;
        this.loggedWith = DEFAULT_LOGIN;
    }

    public User() {

    }

    public static User create(String name, String password, String secretKey, String qrAuthenticator, boolean administrator, String profileImage) {
        return new User(
                name,
                password,
                secretKey,
                qrAuthenticator,
                administrator,
                profileImage
        );
    }

    public void addLoginAttempt(Integer loginAttempts, LocalDateTime lastLogin) {
        this.loginAttempts = loginAttempts;
        this.lastLogin = lastLogin;
    }

    public void changeStatus(UserStatus status) {
        this.status = status;
    }

    public void resetLoginAttempt(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public void addCodeVerification(String codeVerification) {
        this.codeVerification = codeVerification;
        this.codeVerificationCreatedAt = LocalDateTime.now();
    }

    public void resetCodeVerification() {
        this.codeVerification = null;
    }

    public void addLoginAttemptMfa(Integer loginAttemptsMfa) {
        this.loginAttemptsMfa = loginAttemptsMfa;
    }

    public void updateLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }


    public void updateMfaIsEmail(boolean mfaIsEmail) {
        this.mfaIsEmail = mfaIsEmail;
    }

    public void updateAuthenticator(String secretKey, String qrAuthenticator) {
        this.secretKey = secretKey;
        this.qrAuthenticator = qrAuthenticator;
    }

    public void resetPassword(String password, UserStatus status, LocalDateTime lastPasswordUpdatedAt, Integer loginAttempts, Integer loginAttemptsMfa) {
        this.password = password;
        this.status = status;
        this.lastPasswordUpdatedAt = lastPasswordUpdatedAt;
        this.loginAttempts = loginAttempts;
        this.loginAttemptsMfa = loginAttemptsMfa;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void resetLastPasswordAndPasswordHistoryUpdate() {
        this.lastPasswordUpdatedAt = LocalDateTime.now();
        this.loginAttempts = 0;
    }

    public void deactivateAccount(UserStatus status){
        this.status = status;
    }

    public void updateImageAndUserName(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }

    public void addRole(List<Role> roles){
        this.roles = roles;
    }

    public void removeRole(Role role){
        this.roles.remove(role);
    }

    public void resetLastPasswordUpdatedAt(){
        this.lastPasswordUpdatedAt = null;
    }

    public void changeUserName(String userName) {
        this.name = userName;
    }

    public void addPerson(Person person){
        this.person = person;
    }
}
