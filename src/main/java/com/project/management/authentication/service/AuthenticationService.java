package com.project.management.authentication.service;

import com.project.management.authentication.dto.*;
import com.project.management.refreshToken.dto.RefreshTokenRequest;
import com.project.management.refreshToken.dto.RefreshTokenResponse;

import java.util.UUID;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
    LoginResponse authenticationMfa(AuthenticationMfaRequest request);
    void resentEmailByUser(UUID userId);
    void forgotPassword(ResetPasswordRequest request);
    void verifyTokenResetPassword(TokenResentPasswordRequest request);
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
