package com.project.management.user.entity;

import com.project.management.common.util.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

/**
 * Representa el historial de inicio de sesión de un usuario.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "login_history", schema = "main")
public class LoginHistory extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "login_id", nullable = false)
    private UUID loginId;

    /**
     * El usuario asociado al historial de inicio de sesión.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Indica si el inicio de sesión fue exitoso.
     */
    @Column(name = "is_success")
    private Boolean isSuccess;

    /**
     * El mensaje asociado al inicio de sesión.
     */
    @Column(name = "message")
    private String message;


    /**
     * Crea una nueva instancia de LoginHistory sin argumentos.
     */
    public LoginHistory() {
    }

    /**
     * Crea una nueva instancia de LoginHistory.
     *
     * @param user      El usuario asociado al historial de inicio de sesión.
     * @param isSuccess Indica si el inicio de sesión fue exitoso.
     */
    public LoginHistory(User user, Boolean isSuccess) {
        this.user = user;
        this.isSuccess = isSuccess;
    }

    /**
     * Crea una nueva instancia de LoginHistory.
     *
     * @param user      El usuario asociado al historial de inicio de sesión.
     * @param isSuccess Indica si el inicio de sesión fue exitoso.
     * @return La nueva instancia de LoginHistory.
     */
    public static LoginHistory create(User user, Boolean isSuccess) {
        return new LoginHistory(user, isSuccess);
    }

    /**
     * Agrega un mensaje al historial de inicio de sesión.
     *
     * @param message El mensaje a agregar.
     */
    public void addMessage(String message) {
        this.message = message;
    }

    public void updateSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}