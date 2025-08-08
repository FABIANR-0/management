package com.project.management.role.entity;

import com.project.management.permission.entity.Permission;
import com.project.management.user.entity.User;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Metamodelo estático para la entidad Role.
 */
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

    /**
     * Atributo estático para el ID del rol.
     */
    public static volatile SingularAttribute<Role, UUID> roleId;

    /**
     * Atributo estático para el nombre del rol.
     */
    public static volatile SingularAttribute<Role, String> name;

    /**
     * Atributo estático para el estado del rol.
     */
    public static volatile SingularAttribute<Role, Boolean> isActive;

    /**
     * Atributo estático para los permisos asociados al rol.
     */
    public static volatile ListAttribute<Role, Permission> permissions;

    /**
     * Atributo estático para los usuarios asociados al rol.
     */
    public static volatile ListAttribute<Role, User> users;

    /**
     * Atributo estático para la fecha y hora en que se creó el rol.
     */
    public static volatile SingularAttribute<Role, Timestamp> createdAt;

    /**
     * Atributo estático para la fecha y hora en que se actualizó por última vez el rol.
     */
    public static volatile SingularAttribute<Role, Timestamp> updatedAt;
}
