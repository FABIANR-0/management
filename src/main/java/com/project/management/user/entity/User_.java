package com.project.management.user.entity;

import com.project.management.role.entity.Role;
import com.project.management.user.status.UserStatus;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Metamodelo estático para la entidad User.
 */
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

    /**
     * Identificador único del usuario.
     */
    public static volatile SingularAttribute<User, UUID> userId;

    /**
     * Nombre del usuario.
     */
    public static volatile SingularAttribute<User, String> name;

    /**
     * Estado del usuario.
     */
    public static volatile SingularAttribute<User, UserStatus> status;

    /**
     * Indicador de si el usuario es un administrador.
     */
    public static volatile SingularAttribute<User, Boolean> administrator;


    /**
     * Contraseña del usuario.
     */
    public static volatile SingularAttribute<User, String> password;

    /**
     * Fecha y hora de la última actualización de la contraseña.
     */
    public static volatile SingularAttribute<User, LocalDateTime> lastPasswordUpdatedAt;

    /**
     * Código de verificación del usuario.
     */
    public static volatile SingularAttribute<User, String> codeVerification;

    /**
     * Fecha y hora de creación del código de verificación.
     */
    public static volatile SingularAttribute<User, LocalDateTime> codeVerificationCreatedAt;

    /**
     * Fecha de último inicio de sesión del usuario.
     */
    public static volatile SingularAttribute<User, LocalDate> lastLogin;

    /**
     * Intentos de inicio de sesión del usuario.
     */
    public static volatile SingularAttribute<User, Integer> loginAttempts;

    /**
     * Intentos de inicio de sesión MFA (Autenticación Multifactor) del usuario.
     */
    public static volatile SingularAttribute<User, Integer> loginAttemptsMfa;

    /**
     * Indicador de si la autenticación MFA se realiza a través del correo electrónico.
     */
    public static volatile SingularAttribute<User, Boolean> mfaIsEmail;

    /**
     * Clave secreta para la autenticación MFA.
     */
    public static volatile SingularAttribute<User, String> secretKey;

    /**
     * Indicador de si el usuario está bloqueado.
     */
    public static volatile SingularAttribute<User, Boolean> locked;

    /**
     * Fecha y hora de bloqueo del usuario.
     */
    public static volatile SingularAttribute<User, LocalDateTime> lockDate;

    /**
     * Código QR para la autenticación MFA.
     */
    public static volatile SingularAttribute<User, String> qrAuthenticator;


    /**
     * Imagen de perfil del usuario.
     */
    public static volatile SingularAttribute<User, String> profileImage;


    /**
     * Persona asociada al usuario.
     */
    public static volatile SingularAttribute<User, Person> person;

    /**
     * Fecha y hora de creación del usuario.
     */
    public static volatile SingularAttribute<User, LocalDateTime> createdAt;

    /**
     * Fecha y hora de actualización del usuario.
     */
    public static volatile SingularAttribute<User, LocalDateTime> updatedAt;

    /**
     * Atributo estatico para la relacion de user y role.
     */
    public static volatile ListAttribute<User, Role> roles;

    /**
     * Atributo estatico para el historial de contraseñas del usuario.
     */
    public static volatile ListAttribute<User, PasswordHistory> passwordHistories;

    public static volatile SingularAttribute<User,String> loggedWith;

    /**
     * Token de actualización de autenticación.
     */
    public static volatile SingularAttribute<User, RefreshToken> refreshToken;

    public static volatile SingularAttribute<User, Boolean> loginMicrosoft;

    public static volatile SingularAttribute<User, String> microsoftId;
}
