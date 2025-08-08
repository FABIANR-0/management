package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * Clase para actualizar los datos de la persona de un usuario.
 */
@Getter
public class CustomerUpdateRequest {

    /**
     * Username del usuario.
     */
    @NotNull
    @NotEmpty
    @JsonProperty(value = "user_name")
    private String userName;

    /**
     * Foto de perfil del usuario.
     */
    @JsonProperty(value = "user_profile_image")
    private String imageProfile;


    /**
     * Datos de la persona asociada al usuario.
     */
    @Valid
    @JsonProperty(value = "person")
    private PersonUpdateRequest person;
}
