package com.project.management.user.dto;

import com.project.management.common.validation.annotation.PersonEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PersonCreateDto {

    /**
     * Nombre de la persona.
     */
    @NotBlank(message = "el campo nombres no debe estar vacío.")
    @Size(min = 2, max = 50)
    @JsonProperty(value = "person_name")
    private String name;

    /**
     * Apellido de la persona.
     */
    @NotBlank(message = "el campo apellidos no debe estar vacío.")
    @Size(min = 2, max = 38)
    @JsonProperty(value = "person_lastname")
    private String lastname;


    /**
     * Teléfono de la persona.
     */
    @NotBlank
    @Size(min = 8, max = 15)
    @JsonProperty(value = "person_phone")
    private String phone;

    /**
     * Correo electrónico de la persona.
     */
    @NotBlank
    @Size(max = 50)
    @Email
    @PersonEmail
    @JsonProperty(value = "person_email")
    private String email;

    @NotBlank
    @Size(max = 50)
    @JsonProperty(value = "charge")
    private String charge;

    public PersonCreateDto(String name, String lastname, String phone, String email,String charge) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.charge = charge;
    }

    public  static PersonCreateDto create(String name, String lastname, String phone, String email,String charge){
        return new PersonCreateDto(
                name,
                lastname,
                phone,
                email,
                charge
        );
    }
}
