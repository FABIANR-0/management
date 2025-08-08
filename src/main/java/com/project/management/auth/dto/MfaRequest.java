package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

/**
 * Solicitud de autenticación MFA (Autenticación Multifactor) para el cliente.
 */
@Getter
public class MfaRequest {

    /**
     * ID del usuario.
     */
    @JsonProperty(value = "user_id")
    private UUID userId;

    /**
     * Indica si es un nuevo dispositivo.
     */
    @JsonProperty(value = "is_new_dispositive")
    private Boolean isNewDispositive;

    /**
     * Código de usuario.
     */
    @JsonProperty(value = "user_code")
    @NotNull
    @NotEmpty
    private String code;

    /**
     * Dirección IP del cliente.
     */
    @JsonProperty(value = "ip_address")
    private String ipAddress;
}
