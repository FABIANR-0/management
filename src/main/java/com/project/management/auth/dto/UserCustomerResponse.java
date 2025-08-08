package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Respuesta del usuario cliente.
 */
public class UserCustomerResponse {

    /**
     * ID del usuario.
     */
    @JsonProperty(value = "user_id")
    private UUID userId;

    /**
     * Nombre del usuario.
     */
    @JsonProperty(value = "user_name")
    private String name;

    /**
     * Si el usuario es administrador.
     */
    @JsonProperty(value = "is_administrator")
    private boolean isAdministrator;

    @JsonProperty(value = "is_technician")
    private boolean isTechnician;

    /**
     * Estado del usuario.
     */
    @JsonProperty(value = "user_status")
    private String status;

    /**
     * URL de la imagen de perfil del usuario.
     */
    @JsonProperty(value = "user_profile_image")
    private String profileImage;

    /**
     * Nombre del cliente.
     */
    @JsonProperty(value = "person_name")
    private String personName;

    /**
     * Apellido del cliente.
     */
    @JsonProperty(value = "person_last_name")
    private String personLastName;


    /**
     * Teléfono del cliente.
     */
    @JsonProperty(value = "person_phone")
    private String phone;

    /**
     * Correo electrónico del cliente.
     */
    @JsonProperty(value = "person_email")
    private String email;

    @JsonProperty(value = "charge")
    private String charge;

    /**
     * Constructor de UserCustomerResponse.
     *
     * @param userId         ID del usuario.
     * @param name           Nombre del usuario.
     * @param status         Estado del usuario.
     * @param profileImage   URL de la imagen de perfil del usuario.
     * @param personName     Nombre de la persona.
     * @param personLastName Apellido del cliente.
     * @param phone          Teléfono del cliente.
     * @param email          Correo electrónico del cliente.
     */
    public UserCustomerResponse(UUID userId, String name, boolean isAdministrator, boolean isTechnician, String status, String profileImage, String personName,
                                String personLastName, String phone, String email, String charge) {
        this.userId = userId;
        this.name = name;
        this.isAdministrator = isAdministrator;
        this.isTechnician = isTechnician;
        this.status = status;
        this.profileImage = profileImage;
        this.personName = personName;
        this.personLastName = personLastName;
        this.phone = phone;
        this.email = email;
        this.charge = charge;
    }

    /**
     * Crea una nueva instancia de UserCustomerResponse.
     *
     * @param userId       ID del usuario.
     * @param nameUser     Nombre del usuario.
     * @param status       Estado del usuario.
     * @param profileImage URL de la imagen de perfil del usuario.
     * @param namePerson   Nombre de la persona.
     * @param lastname     Apellido del cliente.
     * @param phone        Teléfono del cliente.
     * @param email        Correo electrónico del cliente.
     */
    public static UserCustomerResponse create(UUID userId, String nameUser, boolean isAdministrator, boolean isTechnician, String status, String profileImage, String namePerson,
                                              String lastname, String phone, String email, String charge) {
        return new UserCustomerResponse(
                userId,
                nameUser,
                isAdministrator,
                isTechnician,
                status,
                profileImage,
                namePerson,
                lastname,
                phone,
                email,
                charge
        );
    }

    public UserCustomerResponse(UUID userId, String name, String status, String personName, String phone, String email,String charge) {
        this.userId = userId;
        this.name = name;
        this.status = status;
        this.personName = personName;
        this.phone = phone;
        this.email = email;
        this.charge = charge;
    }

    public static UserCustomerResponse response(UUID userId, String name, String status, String personName, String phone, String email,String charge) {
        return new UserCustomerResponse(
                userId,
                name,
                status,
                personName,
                phone,
                email,
                charge
        );
    }
}
