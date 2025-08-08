package com.project.management.authentication.controller;

import com.project.management.authentication.dto.*;
import com.project.management.authentication.service.AuthenticationService;
import com.project.management.refreshToken.dto.RefreshTokenRequest;
import com.project.management.refreshToken.dto.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/security/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    @Operation(description = "Login Of Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }

    @PostMapping("/mfa")
    @Operation(description = "Verification code by User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    })
    public ResponseEntity<LoginResponse> mfa(@Valid @RequestBody AuthenticationMfaRequest request) {
        return new ResponseEntity<>(authenticationService.authenticationMfa(request), HttpStatus.OK);
    }

    @GetMapping("/resent_email/{id}")
    @Operation(description = "Resent email the code verification of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "500",description = "Server error")
    })
    public ResponseEntity<HttpStatus> resentEmailByUser(@Parameter(description = "UUID of a user",required = true) @PathVariable("id")UUID userId){
        authenticationService.resentEmailByUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/forgot_password")
    @Operation(description = "Forgot password by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "401",description = "UNAUTHORIZED")
    })
    public ResponseEntity<HttpStatus> forgotPassword(@Valid @RequestBody ResetPasswordRequest request){
        authenticationService.forgotPassword(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    @Operation(description = "Reset password by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "401",description = "UNAUTHORIZED")
    })
    public ResponseEntity<HttpStatus> resetPassword(@Valid @RequestBody TokenResentPasswordRequest request){
        authenticationService.verifyTokenResetPassword(request);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh_token")
    @Operation(description = "Refresh token by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "401",description = "UNAUTHORIZED")
    })
    public ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request){
        return  new ResponseEntity<>(authenticationService.refreshToken(request),HttpStatus.OK);
    }
}
