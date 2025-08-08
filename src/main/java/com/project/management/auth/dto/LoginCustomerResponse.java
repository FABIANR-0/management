package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Respuesta de inicio de sesión para el cliente.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginCustomerResponse {

    /**
     * ID del usuario.
     */
    @JsonProperty(value = "user_id")
    private UUID userId;

    /**
     * Indica si el MFA (autenticación multifactor) es a través de correo electrónico.
     */
    @JsonProperty(value = "mfa_is_email")
    private boolean mfaIsEmail;

    /**
     * Correo electrónico del usuario.
     */
    @JsonProperty(value = "email")
    private String email;

    /**
     * Constructor de LoginCustomerResponse.
     *
     * @param userId     ID del usuario.
     * @param mfaIsEmail Indica si el MFA es a través de correo electrónico.
     * @param email      Correo electrónico del usuario.
     */
    public LoginCustomerResponse(UUID userId, Boolean mfaIsEmail, String email) {
        this.userId = userId;
        this.mfaIsEmail = mfaIsEmail;
        this.email = email;
    }

    /**
     * Crea una nueva instancia de LoginCustomerResponse.
     *
     * @param userId     ID del usuario.
     * @param mfaIsEmail Indica si el MFA es a través de correo electrónico.
     * @param email      Correo electrónico del usuario.
     * @return La instancia de LoginCustomerResponse creada.
     */
    public static LoginCustomerResponse create(UUID userId, Boolean mfaIsEmail, String email) {
        return new LoginCustomerResponse(userId, mfaIsEmail, email);
    }
}
