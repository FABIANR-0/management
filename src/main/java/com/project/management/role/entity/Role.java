package com.project.management.role.entity;

import com.project.management.permission.entity.Permission;
import com.project.management.user.entity.User;
import com.project.management.common.util.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "role", schema = "main")
public class Role extends AuditEntity {

    /**
     * El ID del rol.
     */
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private UUID roleId;

    /**
     * El nombre del rol.
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * Indica si el rol está activo.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    /**
     * Los permisos asociados al rol.
     */
    @JoinTable(
            name = "role_permission", schema = "main",
            joinColumns = @JoinColumn(name = "role_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "permission_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "permission_id"}, name = "uc_role_permission")
    )
    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<Permission> permissions;

    /**
     * Los Roles asiganados a un usuario.
     */
    @ManyToMany(mappedBy = "roles")
    private List<User> users;


    public Role(){}

    public Role(String name) {
        this.name = name;
        this.isActive = true;
    }

    public static Role create(String name){
        return new Role(name);
    }

    /**
     * Añade una lista de permisos al rol
     *
     * @param permissions lista de permisos a agregar
     */
    public  void  addPermissions(List<Permission> permissions){
        this.permissions = permissions;
    }

}
