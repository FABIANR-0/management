package com.proyecto.demo.permission.entity;

import com.proyecto.demo.module.entity.Module;
import com.proyecto.demo.role.entity.Role;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permission_.class)
public abstract class Permission_ {
    public static volatile SingularAttribute<Permission, UUID> permissionId;
    public static volatile SingularAttribute<Permission,String> title;
    public static volatile SingularAttribute<Permission,String> permissionName;
    public static volatile SingularAttribute<Permission,Boolean> checked;
    public static volatile SetAttribute<Permission, Role> roles;
    public static volatile SingularAttribute<Permission, Module> module;
}
