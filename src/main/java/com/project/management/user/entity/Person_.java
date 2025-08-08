package com.project.management.user.entity;

import com.project.management.documentType.entity.DocumentType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person_.class)
public abstract class Person_ {
    public static volatile SingularAttribute<Person, UUID> personId;
    public static volatile SingularAttribute<Person,User> user;
    public static volatile SingularAttribute<Person,String> name;
    public static volatile SingularAttribute<Person,String> lastname;
    public static volatile SingularAttribute<Person, DocumentType> documentType;
    public static volatile SingularAttribute<Person,String> documentNumber;
    public static volatile SingularAttribute<Person,String> phone;
    public static volatile SingularAttribute<Person,String> email;
    public static volatile SingularAttribute<Person,String> direction;
    public static volatile SingularAttribute<Person,String> charge;
    public static volatile SingularAttribute<Person, LocalDate> Birthdate;
}
