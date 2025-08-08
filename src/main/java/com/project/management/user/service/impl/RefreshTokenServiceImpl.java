package com.project.management.user.service.impl;

import com.project.management.auth.dto.RefreshTokenResponse;
import com.project.management.auth.dto.refreshTokenRequest;
import com.project.management.auth.exception.RefreshTokenException;
import com.project.management.auth.security.jwt.JwtTokenProvider;
import com.project.management.user.entity.RefreshToken;
import com.project.management.user.entity.User;
import com.project.management.user.repository.RefreshTokenRepository;
import com.project.management.user.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase que proporciona servicios relacionados con los tokens de actualización.
 */
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${settings.auth.token-time}")
    private Integer tokenRefreshTime;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructor de la clase RefreshTokenService.
     *
     * @param refreshTokenRepository Repositorio de tokens de actualización
     */
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, JwtTokenProvider jwtTokenProvider) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.create(user);
        return refreshTokenRepository.save(refreshToken);
    }

    private RefreshToken verifyExpiration(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .map(token -> {
                    if (Duration.between(token.getExpired(), LocalDateTime.now()).toMinutes() >= tokenRefreshTime) {
                        throw new RefreshTokenException("El token ha expirado.");
                    }
                    return token;
                }).orElseThrow(() -> new RefreshTokenException("El token proporcionado no existe."));
    }

    public RefreshTokenResponse refreshToken(refreshTokenRequest token) {
        RefreshToken refreshToken = this.verifyExpiration(token.getRefreshTokenId());
        User user = refreshToken.getUser();
        return RefreshTokenResponse.create(this.createRefreshToken(user).getToken(), jwtTokenProvider.generateToken(user.getName()));
    }

    public void deleteByUser(UUID userId) {
        refreshTokenRepository.deleteByUser_UserId(userId);
    }
}
