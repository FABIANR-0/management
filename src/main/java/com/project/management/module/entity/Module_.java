package com.project.management.module.entity;

import com.project.management.module.type.entity.Type;
import com.project.management.permission.entity.Permission;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Module.class)
public abstract class Module_ {

    /**
     * Atributo estático para el ID del modulo.
     */
    public static volatile SingularAttribute<Module, UUID> moduleId;

    public static volatile SingularAttribute<Module, UUID> parentId;

    /**
     * Atributo estático para el nombre del modulo.
     */
    public static volatile SingularAttribute<Module, String> name;

    /**
     * Atributo estático para la descripción del modulo.
     */
    public static volatile SingularAttribute<Module, String> description;

    /**
     * Atributo estático para la ruta del modulo.
     */
    public static volatile SingularAttribute<Module, String> route;

    /**
     * Atributo estático para el orden del modulo.
     */
    public static volatile SingularAttribute<Module, Integer> order;

    /**
     * Atributo estático para los permisos asociados al módulo.
     */
    public static volatile ListAttribute<Module, Permission> permissions;

    /**
     * Atributo estático para los tipos asociados al módulo.
     */
    public static volatile SetAttribute<Module, Type> types;

    /**
     * Atributo estático para fecha de creación del modulo.
     */
    public static volatile SingularAttribute<Module, LocalDateTime> createdAt;

    /**
     * Atributo estático para fecha de actualización del modulo.
     */
    public static volatile SingularAttribute<Module, LocalDateTime> updatedAt;

    /**
     * Atributo estático para el icono secundario 1
     */
    public static volatile SingularAttribute<Module, String> primaryIcon;

    /**
     * Atributo estático para el icono secundario 2
     */
    public static volatile SingularAttribute<Module, String> secondaryIcon;

    /**
     * Atributo estático para el icono secundario 3
     */
    public static volatile SingularAttribute<Module, String> tertiaryIcon;
}
