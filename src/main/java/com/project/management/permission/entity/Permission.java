package com.project.management.permission.entity;

import com.project.management.module.entity.Module;
import com.project.management.role.entity.Role;
import com.project.management.common.util.AuditEntity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

/**
 * Entidad que representa un permiso en el sistema.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "permission", schema = "main")
public class Permission extends AuditEntity {

    /**
     * Id del permiso.
     */
    @Id
    @GeneratedValue
    @Column(name = "permission_id")
    private UUID permissionId;

    /**
     * Nombre del permiso.
     */
    @Column(name = "name", unique = true, length = 50, nullable = false)
    private String name;

    /**
     * Título del permiso.
     */
    @Column(name = "title", unique = true, length = 100, nullable = false)
    private String title;

    /**
     * Indica si el permiso está marcado o no.
     */
    @Transient
    private Boolean checked;

    /**
     * Roles que tienen este permiso.
     */
    @ManyToMany(mappedBy = "permissions")
    @Fetch(FetchMode.SUBSELECT)
    private List<Role> roles;

    @Column(name = "module_id", nullable = false)
    private UUID moduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false,insertable = false, updatable = false)
    private Module module;


    /**
     * Crea una nueva instancia de Permission sin argumentos.
     */
    public Permission() {
    }

    /**
     * Crea una nueva instancia de Permission con los atributos especificados.
     *
     * @param name   El nombre del permiso.
     * @param title  El título del permiso.
     * @param module El módulo asociado al permiso.
     */
    public Permission(String name, String title, Module module) {
        this.name = name;
        this.title = title;
        this.module = module;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
