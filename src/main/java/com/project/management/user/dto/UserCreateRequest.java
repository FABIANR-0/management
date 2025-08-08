package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * Clase de solicitud para crear un usuario.
 */
@Getter
public class UserCreateRequest {

    /**
     * Nombre del usuario.
     */
    @NotNull
    @NotEmpty
    @JsonProperty(value = "user_name")
    @Size(min = 5, max = 38)
    private String name;

    /**
     * Contraseña del usuario.
     */
    @NotNull
    @NotEmpty
    @JsonProperty(value = "user_password")
    private String password;

    /**
     * Confirmación de contraseña del usuario.
     */
    @NotNull
    @NotEmpty
    @JsonProperty(value = "user_password_confirm")
    private String passwordConfirmed;

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
    private PersonCreateRequest person;

    @NotBlank
    @JsonProperty(value = "commerce_url_path")
    private String urlPath;
}
