package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * Clase que representa una solicitud de inicio de sesión para el cliente.
 */
@Getter
public class LoginCustomerRequest {

    /**
     * Nombre del cliente.
     */
    @Size(max = 30)
    @NotNull
    @NotEmpty
    @JsonProperty(value = "user_name")
    private String name;

    /**
     * Contraseña del cliente.
     */
    @Size(max = 30)
    @NotNull
    @NotEmpty
    @JsonProperty(value = "user_password")
    private String password;
}
