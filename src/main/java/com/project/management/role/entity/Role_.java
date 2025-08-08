package com.project.management.role.entity;

import com.project.management.permission.entity.Permission;
import com.project.management.user.entity.User;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {
    public static volatile SingularAttribute<Role, UUID> roleId;
    public static volatile SingularAttribute<Role,String> roleName;
    public static volatile SetAttribute<Role, User> users;
    public static volatile SetAttribute<Role, Permission> permissions;
}
