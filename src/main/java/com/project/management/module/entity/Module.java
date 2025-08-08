package com.project.management.module.entity;

import com.project.management.module.type.entity.Type;
import com.project.management.permission.entity.Permission;
import com.project.management.common.util.AuditEntity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Representa un módulo en el sistema de comercio electrónico.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "module", schema = "main")
public class Module extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "module_id")
    private UUID moduleId;

    @Column(name = "parent_id")
    private UUID parentId;

    /**
     * El nombre del módulo.
     */
    @Column(name = "name", unique = true, length = 50, nullable = false)
    private String name;

    /**
     * La descripción del módulo.
     */
    @Column(name = "description", unique = true, length = 200, nullable = false)
    private String description;

    /**
     * La ruta del módulo.
     */
    @Column(name = "route", unique = true, length = 50, nullable = false)
    private String route;

    /**
     * El orden del módulo.
     */
    @Column(name = "module_order", nullable = false)
    private Integer order;

    /**
     * Los permisos asociados al módulo.
     */
    @OneToMany(mappedBy = "module")
    private List<Permission> permissions;

    /**
     * Los tipos asociados al módulo.
     */
    @JoinTable(
            name = "module_type", schema = "main",
            joinColumns = @JoinColumn(name = "module_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "type_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"module_id", "type_id"}, name = "uc_module_type")
    )
    @ManyToMany
    private Set<Type> types;

    @Column(name = "primary_icon")
    private String primaryIcon;

    @Column(name = "secondary_icon")
    private String secondaryIcon;

    @Column(name = "tertiary_icon")
    private String tertiaryIcon;

    /**
     * Crea una nueva instancia de Module sin argumentos.
     */
    public Module() {
    }

    public Module(String name, String description, String route, Integer order, List<Permission> permissions, Set<Type> types, String primaryIcon, String secondaryIcon, String tertiaryIcon) {
        this.name = name;
        this.description = description;
        this.route = route;
        this.order = order;
        this.permissions = permissions;
        this.types = types;
        this.primaryIcon = primaryIcon;
        this.secondaryIcon = secondaryIcon;
        this.tertiaryIcon = tertiaryIcon;
    }

    public static Module create(String name, String description, String route, Integer order, List<Permission> permissions, Set<Type> types, String primaryIcon, String secondaryIcon, String tertiaryIcon) {
        return new Module(
                name,
                description,
                route,
                order,
                permissions,
                types,
                primaryIcon,
                secondaryIcon,
                tertiaryIcon
        );
    }

}
