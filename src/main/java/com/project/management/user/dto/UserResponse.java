package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class UserResponse {

    /**
     * Foto de perfil del usuario.
     */
    @NotBlank
    @JsonProperty(value = "user_profile_image")
    private String imageProfile;

    /**
     * Username del usuario.
     */
    @NotNull
    @NotEmpty
    @JsonProperty(value = "user_name")
    private String userName;

    /**
     * Nombre de la persona.
     */
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    @JsonProperty(value = "person_name")
    private String name;

    /**
     * Apellido de la persona.
     */
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 38)
    @JsonProperty(value = "person_lastname")
    private String lastname;

    /**
     * Teléfono de la persona.
     */
    @NotNull
    @NotEmpty
    @Size(min = 8, max = 15)
    @JsonProperty(value = "person_phone")
    private String phone;

    /**
     * Correo electrónico de la persona.
     */
    @NotNull
    @NotEmpty
    @Size(max = 50)
    @Email
    @JsonProperty(value = "person_email")
    private String email;

    @NotBlank
    @Size(max = 50)
    @JsonProperty(value = "charge")
    private String charge;

    /**
     * Constructor para crear una instancia de UserResponse con los campos especificados.
     *
     * @param imageProfile     La imagen del usuario.
     * @param userName         El nombre de usuario.
     * @param name             El nombre de la persona.
     * @param lastname         El apellido de la persona.
     * @param phone            El telefono de la persona.
     * @param email            El correo de la persona.
     */
    public UserResponse(String imageProfile, String userName, String name, String lastname, String phone, String email,String charge) {
        this.imageProfile = imageProfile;
        this.userName = userName;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.charge = charge;
    }

    /**
     * Método estático para crear una instancia de UserResponse utilizando los valores proporcionados.
     *
     * @param imageProfile     La imagen del usuario.
     * @param userName         El nombre de usuario.
     * @param name             El nombre de la persona.
     * @param lastname         El apellido de la persona.
     * @param phone            El telefono de la persona.
     * @param email            El correo de la persona.
     *
     * @return Una nueva instancia de UserResponse.
     */
    public static UserResponse response(String imageProfile, String userName, String name, String lastname, String phone, String email,String charge) {
        return new UserResponse(
                imageProfile,
                userName,
                name,
                lastname,
                phone,
                email,
                charge
        );
    }

}
