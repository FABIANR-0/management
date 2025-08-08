package com.proyecto.demo.refreshToken.service.impl;

import com.proyecto.demo.common.exception.service.AuthenticationFailedException;
import com.proyecto.demo.refreshToken.entity.RefreshToken;
import com.proyecto.demo.refreshToken.repository.RefreshTokenRepository;
import com.proyecto.demo.refreshToken.service.RefreshTokenShared;
import com.proyecto.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenShared {

    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${settings.auth.token-time}")
    private int timeToken;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public UUID createRefreshToken(User user) {
        return refreshTokenRepository.save(new RefreshToken(user)).getToken();
    }

    @Override
    public void deleteRefreshTokenByUser(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user);
        refreshTokenRepository.delete(refreshToken);
    }

    @Override
    public RefreshToken evaluateRefreshToken(UUID refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken).map(token -> {
            Duration duration = Duration.between(token.getExpiredToken(), LocalDateTime.now());
            if (duration.toMinutes() >= timeToken){
                throw new AuthenticationFailedException("refresh token expirado.");
            }
            return token;
        }).orElseThrow(()-> new AuthenticationFailedException("el token no se encuentra registrado"));
    }
}
