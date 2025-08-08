package com.project.management.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * La clase PasswordHistory representa el historial de contraseñas de un usuario.
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "password_history", schema = "main")
public class PasswordHistory {

    @Id
    @GeneratedValue
    @Column(name = "password_history_id")
    private UUID passwordHistoryId;

    @Column(name = "password_history_value", nullable = false)
    private String passwordHistoryValue;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PasswordHistory(String passwordHistoryValue, User user) {
        this.passwordHistoryValue = passwordHistoryValue;
        this.user = user;
    }

    public static PasswordHistory create(String passwordHistoryValue, User user){
        return new PasswordHistory(passwordHistoryValue,user);
    }
}
