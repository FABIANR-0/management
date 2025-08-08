package com.proyecto.demo.authentication.service;

import com.proyecto.demo.authentication.dto.*;
import com.proyecto.demo.refreshToken.dto.RefreshTokenRequest;
import com.proyecto.demo.refreshToken.dto.RefreshTokenResponse;

import java.util.UUID;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
    LoginResponse authenticationMfa(AuthenticationMfaRequest request);
    void resentEmailByUser(UUID userId);
    void forgotPassword(ResetPasswordRequest request);
    void verifyTokenResetPassword(TokenResentPasswordRequest request);
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
