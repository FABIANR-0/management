package com.project.management.authentication.service;

import com.project.management.authentication.dto.LoginRequest;
import com.project.management.authentication.dto.LoginResponse;
import com.project.management.authentication.dto.ResetPasswordRequest;
import com.project.management.authentication.dto.TokenResentPasswordRequest;
import com.project.management.refreshToken.dto.RefreshTokenRequest;
import com.project.management.refreshToken.dto.RefreshTokenResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);

    void forgotPassword(ResetPasswordRequest request);

    void verifyTokenResetPassword(TokenResentPasswordRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
