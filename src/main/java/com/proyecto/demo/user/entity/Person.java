package com.proyecto.demo.user.entity;

import com.proyecto.demo.common.util.AuditEntity;
import com.proyecto.demo.documentType.entity.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "person",schema = "main")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Person extends AuditEntity {

    @Id
    private UUID personId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;

    @ManyToOne
    @JoinColumn(name = "document_type_id",nullable = false)
    private DocumentType documentType;

    @Column(name = "document_number", unique = true, nullable = false, length = 15)
    private String documentNumber;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "email", nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "direction", length = 30)
    private String direction;

    @Column(name = "charge", nullable = false, length = 50)
    private String charge;

    @Column(name = "birthdate", nullable = false)
    private LocalDate Birthdate;

    public Person() {
    }

    public Person(String name, String lastname, String documentNumber, String phone, String email, String charge, LocalDate birthdate) {
        this.name = name;
        this.lastname = lastname;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.email = email;
        this.charge = charge;
        Birthdate = birthdate;
    }

    public static Person create(String name, String lastname, String documentNumber, String phone, String email, String charge, LocalDate birthdate) {
        return new Person(
                name,
                lastname,
                documentNumber,
                phone,
                email,
                charge,
                birthdate
        );
    }

    public void addUser(User user){
        this.user = user;
    }

    public void addDocumentType(DocumentType documentType){
        this.documentType = documentType;
    }
}
