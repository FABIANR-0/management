package com.project.management.user.entity;

import com.project.management.refreshToken.entity.RefreshToken;
import com.project.management.role.entity.Role;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {
    public static volatile SingularAttribute<User, UUID> userId;
    public static volatile SingularAttribute<User,String> userName;
    public static volatile SingularAttribute<User,String> password;
    public static volatile SingularAttribute<User,Boolean> locked;
    public static volatile SingularAttribute<User,Integer> loginAttempts;
    public static volatile SingularAttribute<User,Integer> loginAttemptsMfa;
    public static volatile SingularAttribute<User,Boolean> administrator;
    public static volatile SingularAttribute<User,String> codeVerification;
    public static volatile SingularAttribute<User,String> profileImage;
    public static volatile SingularAttribute<User, LocalDateTime> createCodeVerification;
    public static volatile SingularAttribute<User,Person> person;
    public static volatile SetAttribute<User, Role> roles;
    public static volatile SingularAttribute<User,Integer> quantityResentEmail;
    public static volatile SingularAttribute<User,LocalDateTime> resetPasswordUpdateAt;
    public static volatile SingularAttribute<User, RefreshToken> refreshToken;
}
