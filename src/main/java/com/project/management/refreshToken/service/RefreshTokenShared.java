package com.project.management.refreshToken.service;

import com.project.management.refreshToken.entity.RefreshToken;
import com.project.management.user.entity.User;

import java.util.UUID;

public interface RefreshTokenShared {
    UUID createRefreshToken(User user);
    void deleteRefreshTokenByUser(User user);
    RefreshToken evaluateRefreshToken(UUID refreshToken);
}
