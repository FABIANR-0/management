package com.project.management.user.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LoginHistory.class)
public abstract class LoginHistory_ {

    public static volatile SingularAttribute<LoginHistory,UUID> loginId;
    public static volatile SingularAttribute<LoginHistory, User> user;
    public static volatile SingularAttribute<LoginHistory,Boolean> isSuccess;
    public static volatile SingularAttribute<LoginHistory, String> message;
}
