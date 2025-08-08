package com.project.management.module.type.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Type.class)
public abstract class Type_ {
    public static volatile SingularAttribute<Module, UUID> typeId;
    public static volatile SingularAttribute<Module, String> name;
}
