package com.project.management.user.entity;

import com.project.management.common.util.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa un token de actualización en el sistema.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "refresh_token", schema = "main")
public class RefreshToken extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "refresh_token_id", nullable = false)
    private UUID refreshTokenId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expired", nullable = false)
    private LocalDateTime expired;

    /**
     * Constructor vacío de la clase RefreshToken.
     */
    public RefreshToken() {
    }

    /**
     * Constructor de la clase RefreshToken.
     *
     * @param user El usuario asociado al token de actualización.
     */
    public RefreshToken(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        this.expired = LocalDateTime.now();
    }

    /**
     * Crea una nueva instancia de la clase RefreshToken para el usuario especificado.
     *
     * @param user El usuario asociado al token de actualización.
     * @return La nueva instancia de la clase RefreshToken creada para el usuario especificado.
     */
    public static RefreshToken create(User user) {
        return new RefreshToken(user);
    }
}
