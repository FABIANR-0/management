package com.project.management.user.service;

import com.project.management.auth.dto.RefreshTokenResponse;
import com.project.management.auth.dto.refreshTokenRequest;
import com.project.management.user.entity.RefreshToken;
import com.project.management.user.entity.User;

import java.util.UUID;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshTokenResponse refreshToken(refreshTokenRequest token);

    void deleteByUser(UUID userId);
}
