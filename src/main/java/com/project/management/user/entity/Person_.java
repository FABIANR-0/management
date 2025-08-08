package com.project.management.user.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Metamodelo estático para la entidad User.
 */
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ {

    /**
     * Atributo estático que representa el ID de la persona.
     */
    public static volatile SingularAttribute<Person, UUID> personId;

    /**
     * Atributo estático que representa el usuario asociado a la persona.
     */
    public static volatile SingularAttribute<Person, User> user;

    /**
     * Atributo estático que representa el nombre de la persona.
     */
    public static volatile SingularAttribute<Person, String> name;

    /**
     * Atributo estático que representa el apellido de la persona.
     */
    public static volatile SingularAttribute<Person, String> lastname;

    /**
     * Atributo estático que representa el número de teléfono de la persona.
     */
    public static volatile SingularAttribute<Person, String> phone;

    /**
     * Atributo estático que representa el correo electrónico de la persona.
     */
    public static volatile SingularAttribute<Person, String> email;

    /**
     * Atributo estático que representa la fecha de creación de la persona.
     */
    public static volatile SingularAttribute<Person, LocalDate> createdAt;

    /**
     * Atributo estático que representa la fecha de actualización de la persona.
     */
    public static volatile SingularAttribute<Person, LocalDate> updatedAt;

    /**
     * Atributo estático para el cargo de la persona.
     */
    public static volatile SingularAttribute<Person,String> charge;
}
