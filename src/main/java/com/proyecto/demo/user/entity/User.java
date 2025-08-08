package com.proyecto.demo.user.entity;

import com.proyecto.demo.common.util.AuditEntity;
import com.proyecto.demo.refreshToken.entity.RefreshToken;
import com.proyecto.demo.role.entity.Role;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Table(name = "user",schema = "main")
@EntityListeners(AuditingEntityListener.class)
public class User extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "user_name",nullable = false,unique = true,length = 50)
    private String userName;

    @Column(name = "password", nullable = false,length = 100)
    private String password;

    @Column(name = "locked",nullable = false)
    private boolean locked;

    @Column(name = "login_attempts")
    private int loginAttempts;

    @Column(name = "login_attempts_mfa")
    private int loginAttemptsMfa;

    @Column(name = "administrator",nullable = false)
    private boolean administrator;

    @Column(name = "code_verification",length = 7)
    private String codeVerification;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "created_code_verification")
    private LocalDateTime createCodeVerification;

    @Column(name = "quantity_resent_email")
    private int quantityResentEmail;

    @Column(name = "reset_password_updateAt")
    private LocalDateTime resetPasswordUpdateAt;

    @OneToOne(mappedBy = "user")
    private Person person;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "user_role",schema = "main",
            joinColumns = @JoinColumn(name = "user_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id",nullable = false),
            uniqueConstraints = @UniqueConstraint(name = "uc_user_id_role_id",columnNames = {"user_id","role_id"})
    )
    Set<Role> roles;

    public void updateLoginAttempts(int attempts){
        this.loginAttempts = attempts;
    }

    public void updateLocked(boolean locked){
        this.locked = locked;
    }

    public void updateLoginAttemptsMfa(int loginAttemptsMfa){
        this.loginAttemptsMfa = loginAttemptsMfa;
    }

    public void updateQuantityResentEmail(int quantityResentEmail){
        this.quantityResentEmail = quantityResentEmail;
    }

    public void resetCodeVerification(){
        this.codeVerification = null;
    }

    public void updateResetPasswordUpdateAt(LocalDateTime resetPasswordUpdateAt){
        this.resetPasswordUpdateAt = resetPasswordUpdateAt;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void addCodeVerification(String codeVerification){
        this.codeVerification = codeVerification;
        this.createCodeVerification = LocalDateTime.now();
    }
}
