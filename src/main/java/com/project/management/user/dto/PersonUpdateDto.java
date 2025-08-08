package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class PersonUpdateDto {

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


    public PersonUpdateDto(String name, String lastname, String phone, String email,String charge) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.charge = charge;
    }

    public  static PersonUpdateDto create(String name, String lastname, String phone, String email,String charge){
        return new PersonUpdateDto(
                name,
                lastname,
                phone,
                email,
                charge
        );
    }
}
