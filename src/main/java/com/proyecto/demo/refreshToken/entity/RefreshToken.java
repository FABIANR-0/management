package com.proyecto.demo.refreshToken.entity;

import com.proyecto.demo.common.util.AuditEntity;
import com.proyecto.demo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_token",schema = "main")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "refresh_token_id")
    private UUID refreshTokenId;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "token",nullable = false,unique = true)
    private UUID token;

    @Column(name = "expired_token")
    private LocalDateTime expiredToken;

    public RefreshToken(User user) {
        this.token = UUID.randomUUID();
        this.expiredToken = LocalDateTime.now();
        this.user = user;
    }

    public RefreshToken() {

    }
}
