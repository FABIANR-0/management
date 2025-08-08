package com.project.management.email.entity;

import com.project.management.common.util.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * Clase que representa una plantilla de correo electrónico.
 */
@Getter
@Accessors(fluent = true)
@Entity
@Table(name = "email_template", schema = "main")
@EntityListeners(AuditingEntityListener.class)
public class EmailTemplate extends AuditEntity {

    /**
     * Nombre de la plantilla de correo electrónico.
     */
    @Id
    @Column(name = "name", unique = true, length = 100)
    private String name;

    /**
     * Cuerpo de la plantilla de correo electrónico.
     */
    @Column(name = "body", columnDefinition = "text")
    private String body;

    /**
     * Reemplaza el cuerpo de la plantilla de correo electrónico.
     *
     * @param body El nuevo cuerpo de la plantilla de correo electrónico.
     */
    public void replaceBody(String body){
        this.body = body;
    }
}
