package com.proyecto.demo.refreshToken.service;

import com.proyecto.demo.refreshToken.entity.RefreshToken;
import com.proyecto.demo.user.entity.User;

import java.util.UUID;

public interface RefreshTokenShared {
    UUID createRefreshToken(User user);
    void deleteRefreshTokenByUser(User user);
    RefreshToken evaluateRefreshToken(UUID refreshToken);
}
