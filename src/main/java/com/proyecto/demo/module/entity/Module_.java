package com.proyecto.demo.module.entity;

import com.proyecto.demo.permission.entity.Permission;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Module.class)
public abstract class Module_ {

    public static volatile SingularAttribute<Module, UUID> moduleId;
    public static volatile SingularAttribute<Module,String> moduleName;
    public static volatile SingularAttribute<Module,String> ico;
    public static volatile SingularAttribute<Module,String> description;
    public static volatile SingularAttribute<Module,String> route;
    public static volatile SingularAttribute<Module,Integer> order;
    public static volatile SetAttribute<Module, Permission> permissions;
}
