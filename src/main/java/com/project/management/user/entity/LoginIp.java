package com.project.management.user.entity;

import com.project.management.common.util.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

/**
 * La clase LoginIp representa la información de una dirección IP utilizada durante el inicio de sesión de un usuario.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "login_ip", schema = "main")
public class LoginIp extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "login_ip_id", nullable = false)
    private UUID loginIpId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "value", length = 15, nullable = false)
    private String value;


    /**
     * Crea una nueva instancia de LoginIp sin argumentos.
     */
    public LoginIp() {
    }

    /**
     * Crea una nueva instancia de LoginIp con el usuario y la dirección IP especificados.
     *
     * @param user  El usuario asociado a la dirección IP.
     * @param value La dirección IP.
     */
    public LoginIp(User user, String value) {
        this.user = user;
        this.value = value;
    }

    /**
     * Crea y devuelve una nueva instancia de LoginIp con el usuario y la dirección IP especificados.
     *
     * @param user  El usuario asociado a la dirección IP.
     * @param value La dirección IP.
     * @return Una nueva instancia de LoginIp.
     */
    public static LoginIp create(User user, String value) {
        return new LoginIp(user, value);
    }
}
