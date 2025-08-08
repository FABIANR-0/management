package com.proyecto.demo.role.entity;

import com.proyecto.demo.common.util.AuditEntity;
import com.proyecto.demo.permission.entity.Permission;
import com.proyecto.demo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@Table(name = "role",schema = "main")
@EntityListeners(AuditingEntityListener.class)
public class Role extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "role_name",nullable = false,unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.REFRESH)
    Set<User> users;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "role_permission",schema = "main",
            joinColumns = @JoinColumn(name = "role_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "permission_id",nullable = false),
            uniqueConstraints = @UniqueConstraint(name = "uc_role_id_permission_id",columnNames = {"role_id","permission_id"})
    )
    private Set<Permission> permissions;
}
