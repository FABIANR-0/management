package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Respuesta del token de acceso.
 */
public class TokenResponse {

    /**
     * Tipo de token.
     */
    @JsonProperty(value = "token_type")
    private String tokenType = "Bearer";

    /**
     * Token de acceso.
     */
    @JsonProperty(value = "access_token")
    private String accessToken;

    /**
     * Token de actualización.
     */
    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    /**
     * Constructor de TokenResponse.
     *
     * @param accessToken  Token de acceso.
     * @param refreshToken Token de actualización.
     */
    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * Crea una nueva instancia de TokenResponse.
     *
     * @param accessToken  Token de acceso.
     * @param refreshToken Token de actualización.
     * @return La instancia de TokenResponse creada.
     */
    public static TokenResponse create(String accessToken, String refreshToken) {
        return new TokenResponse(accessToken, refreshToken);
    }
}
