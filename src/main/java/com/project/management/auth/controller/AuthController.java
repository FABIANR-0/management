package com.project.management.auth.controller;

import com.project.management.auth.dto.*;
import com.project.management.auth.service.AuthService;
import com.project.management.user.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador para las operaciones de autenticación.
 */
@RestController
@RequestMapping("/secure/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Inicia sesión para un cliente.
     *
     * @param request Objeto de solicitud de inicio de sesión del cliente.
     * @return ResponseEntity con la respuesta de inicio de sesión del cliente.
     */
    @Operation(description = "Login for user customer")
    @ApiResponse(responseCode = "200", description = "success")
    @PostMapping("/login")
    public ResponseEntity<AuthCustomerResponse> login(@Valid @RequestBody LoginCustomerRequest request) {
        AuthCustomerResponse response = authService.loginCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Autenticación MFA para un cliente.
     *
     * @param request Objeto de solicitud de autenticación MFA.
     * @return ResponseEntity vacío.
     */
//    @Operation(description = "Mfa for user customer")
//    @ApiResponse(responseCode = "200", description = "success")
//    @PostMapping("/mfa")
//    public ResponseEntity<AuthCustomerResponse> mfa(@Valid @RequestBody MfaRequest request) {
//        return new ResponseEntity<>(authService.mfaAuthenticationCustomer(request), HttpStatus.OK);
//    }

    /**
     * Refresca el token de acceso.
     *
     * @param request El objeto de solicitud de refreshToken
     * @return ResponseEntity con la respuesta del token de acceso actualizado.
     */
    @PostMapping("/refresh_token")
    @Operation(description = "RefreshToken")
    @ApiResponse(description = "Create RefreshToken Success", responseCode = "201")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody refreshTokenRequest request) {
        return new ResponseEntity<>(refreshTokenService.refreshToken(request), HttpStatus.OK);
    }

    /**
     * Reenviar Correo de código de Acceso.
     *
     * @param userId El userId para el reenvío del Correo.
     * @return ResponseEntity Responde un código 200 que el correo se envío con éxito.
     */
    @GetMapping("/resend_email_code/{id}")
    @Operation(description = "Resend Email MFA")
    @ApiResponse(description = "Resend Email Success", responseCode = "200")
    public ResponseEntity<HttpStatus> resendEmailCode(@Parameter(description = "UUID of a user", required = true) @PathVariable("id") UUID userId) {
        authService.resendTwoFactorAuthentication(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
