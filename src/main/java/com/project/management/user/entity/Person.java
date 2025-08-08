package com.project.management.user.entity;

import com.project.management.common.util.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

/**
 * Representa una persona en el sistema.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "person", schema = "main")
public class Person extends AuditEntity {
    /**
     * Atributo que representa el ID de la persona.
     */
    @Id
    private UUID personId;

    /**
     * Relación con un objeto usuario.
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Nombre de la persona.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Apellido de la persona.
     */
    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;

    /**
     * Número de teléfono de la persona.
     */
    @Column(name = "phone", length = 13,nullable = false)
    private String phone;

    /**
     * Dirección de correo electrónico de la persona.
     */
    @Column(name = "email", nullable = false, length = 200, unique = true)
    private String email;


    @Column(name = "charge",nullable = false, length = 200)
    private String charge;

    public Person() {
    }

    /**
     * Constructor de la clase Person.
     *
     * @param name           El nombre de la persona.
     * @param lastname       El apellido de la persona.
     * @param phone          El número de teléfono de la persona.
     * @param email          El correo electrónico de la persona
     */
    public Person(String name, String lastname, String phone, String email,String charge) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.charge = charge;
    }

    /**
     * Crea una nueva instancia de la clase Person con los datos proporcionados.
     *
     * @param name           El nombre de la persona.
     * @param lastname       El apellido de la persona.
     * @param phone          El número de teléfono de la persona.
     * @param email          El correo electrónico de la persona.
     * @return La nueva instancia de la clase Person creada con los datos proporcionados.
     */
    public static Person create(String name, String lastname, String phone, String email,String charge) {
        return new Person(
                name,
                lastname,
                phone,
                email,
                charge
        );
    }

    /**
     *
     * Método para actualizar los datos de una instancia de la clase Person.
     *
     * @param name           El nombre de la persona.
     * @param lastname       El apellido de la persona.
     * @param phone          El número de teléfono de la persona.
     * @param email          El correo electrónico de la persona.
     */
    public void update(String name, String lastname, String phone, String email,String charge) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.charge = charge;
    }

    /**
     * Asigna un usuario al objeto.
     *
     * @param user Usuario a asignar.
     */
    public void addUser(User user) {
        this.user = user;
    }

}
