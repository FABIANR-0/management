package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Respuesta de autenticación para el cliente.
 */
public class AuthCustomerResponse {
    @JsonProperty(value = "token")
    private TokenResponse tokenResponse;

    @JsonProperty(value = "user")
    private UserCustomerResponse userCustomerResponse;

    @JsonProperty(value = "modules")
    private List<AuthModule> modules;

    @JsonProperty(value = "permissions")
    private List<Permission> permissions;

    @JsonProperty(value = "reset_password")
    private String resetPassword;

    /**
     * Constructor de AuthCustomerResponse.
     *
     * @param tokenResponse        La respuesta del token.
     * @param userCustomerResponse La respuesta del usuario cliente.
     */
    public AuthCustomerResponse(TokenResponse tokenResponse, UserCustomerResponse userCustomerResponse, List<AuthModule> modules, List<Permission> permissions, String resetPassword) {
        this.tokenResponse = tokenResponse;
        this.userCustomerResponse = userCustomerResponse;
        this.modules = modules;
        this.permissions = permissions;
        this.resetPassword = resetPassword;
    }

    public AuthCustomerResponse(TokenResponse tokenResponse, UserCustomerResponse userCustomerResponse, List<AuthModule> modules, List<Permission> permissions) {
        this.tokenResponse = tokenResponse;
        this.userCustomerResponse = userCustomerResponse;
        this.modules = modules;
        this.permissions = permissions;
    }

    /**
     * Crea una nueva instancia de AuthCustomerResponse.
     *
     * @param tokenResponse        La respuesta del token.
     * @param userCustomerResponse La respuesta del usuario cliente.
     * @return La instancia de AuthCustomerResponse creada.
     */
    public static AuthCustomerResponse create(TokenResponse tokenResponse, UserCustomerResponse userCustomerResponse, List<AuthModule> modules, List<Permission> permissions, String resetPassword) {
        return new AuthCustomerResponse(tokenResponse,
                userCustomerResponse,
                modules,
                permissions,
                resetPassword
        );
    }

    public static AuthCustomerResponse create(TokenResponse tokenResponse, UserCustomerResponse userCustomerResponse, List<AuthModule> modules, List<Permission> permissions) {
        return new AuthCustomerResponse(tokenResponse,
                userCustomerResponse,
                modules,
                permissions
        );
    }
}
