package com.project.management.permission.entity;

import com.project.management.module.entity.Module;
import com.project.management.role.entity.Role;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permission.class)
public abstract class Permission_ {

    /**
     * Atributo estático que representa el ID del permiso.
     */
    public static volatile SingularAttribute<Permission, UUID> permissionId;

    /**
     * Atributo estático que representa el nombre del permiso.
     */
    public static volatile SingularAttribute<Permission, String> name;

    /**
     * Atributo estático que representa el título del permiso.
     */
    public static volatile SingularAttribute<Permission, String> title;

    /**
     * Atributo estático que indica un booleano que puede usarse para marcar el permiso.
     */
    public static volatile SingularAttribute<Permission, Boolean> checked;

    /**
     * Atributo estático que representa la lista de roles asociados a este permiso.
     */
    public static volatile ListAttribute<Permission, Role> roles;

    /**
     * Atributo estático que representa el módulo al que pertenece este permiso.
     */
    public static volatile SingularAttribute<Permission, Module> module;

    /**
     * Atributo estático que representa la fecha de creación del permiso.
     */
    public static volatile SingularAttribute<Permission, LocalDateTime> createdAt;

    /**
     * Atributo estático que representa la fecha de actualización del permiso.
     */
    public static volatile SingularAttribute<Permission, LocalDateTime> updatedAt;

    public static volatile SingularAttribute<Permission,UUID> moduleId;
}
