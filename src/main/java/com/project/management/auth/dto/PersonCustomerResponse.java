package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Respuesta de información personal del cliente.
 */
public class PersonCustomerResponse {

    /**
     * Nombre del cliente.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * Apellido del cliente.
     */
    @JsonProperty(value = "lastname")
    private String lastname;

    /**
     * Teléfono del cliente.
     */
    @JsonProperty(value = "phone")
    private String phone;

    /**
     * Correo electrónico del cliente.
     */
    @JsonProperty(value = "email")
    private String email;

    /**
     * Constructor de PersonCustomerResponse.
     *
     * @param name           Nombre del cliente.
     * @param lastname       Apellido del cliente.
     * @param phone          Teléfono del cliente.
     * @param email          Correo electrónico del cliente.
     */
    public PersonCustomerResponse(String name, String lastname, String phone, String email) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Crea una nueva instancia de PersonCustomerResponse.
     *
     * @param name           Nombre del cliente.
     * @param lastname       Apellido del cliente.
     * @param phone          Teléfono del cliente.
     * @param email          Correo electrónico del cliente.
     * @return La instancia de PersonCustomerResponse creada.
     */

    public static PersonCustomerResponse create(String name, String lastname, String phone, String email) {
        return new PersonCustomerResponse(
                name,
                lastname,
                phone,
                email
        );
    }
}
