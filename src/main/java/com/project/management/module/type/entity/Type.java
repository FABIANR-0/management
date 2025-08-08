package com.project.management.module.type.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;


@Getter
@Entity
@Table(name = "type", schema = "main")
public class Type {

    /**
     * Identificador único del tipo.
     */
    @Id
    @GeneratedValue
    @Column(name = "type_id")
    private UUID typeId;

    /**
     * Nombre del tipo.
     */
    @Column(name = "type_name", nullable = false, length = 100, unique = true)
    private String name;

    protected Type() {

    }
    public Type(String name) {
        this.name = name;
    }

    /**
     * Crea un nuevo tipo
     * @param name del tipo
     * @return retorna el nuevo tipo.
     */
    public Type create(String name){
        return new Type(name);
    }

    /**
     * Actualiza el nombre del tipo.
     * @param name
     */
    public void update(String name){
        this.name = name;
    }

}
