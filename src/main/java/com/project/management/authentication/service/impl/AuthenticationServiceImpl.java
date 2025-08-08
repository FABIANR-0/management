package com.project.management.authentication.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.management.authentication.dto.LoginRequest;
import com.project.management.authentication.dto.LoginResponse;
import com.project.management.authentication.dto.ResetPasswordRequest;
import com.project.management.authentication.dto.TokenResentPasswordRequest;
import com.project.management.authentication.service.AuthenticationService;
import com.project.management.common.encryption.Encryption;
import com.project.management.common.exception.service.AuthenticationFailedException;
import com.project.management.email.service.EmailSendService;
import com.project.management.refreshToken.dto.RefreshTokenRequest;
import com.project.management.refreshToken.dto.RefreshTokenResponse;
import com.project.management.refreshToken.entity.RefreshToken;
import com.project.management.refreshToken.service.RefreshTokenShared;
import com.project.management.security.jwt.JwtTokenProvider;
import com.project.management.user.entity.User;
import com.project.management.user.service.UserServiceShared;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceShared userServiceShared;
    private final PasswordEncoder passwordEncoder;
    private final EmailSendService emailSendService;
    private final Encryption encryption;
    private final RefreshTokenShared refreshTokenShared;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserServiceShared userServiceShared, PasswordEncoder passwordEncoder, EmailSendService emailSendService, Encryption encryption, RefreshTokenShared refreshTokenShared) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userServiceShared = userServiceShared;
        this.passwordEncoder = passwordEncoder;
        this.emailSendService = emailSendService;
        this.encryption = encryption;
        this.refreshTokenShared = refreshTokenShared;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userServiceShared.getUserByUserName(request.getUserName().toLowerCase(Locale.ROOT).replace(" ", ""));

        if (user.isLocked()) {
            throw new AuthenticationFailedException("el usuario se encuentra bloqueado");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            if (user.getLoginAttempts() >= 2) {
                this.updateLockedUser(user);
            } else {
                this.updateLoginAttemptsUser(user);
            }
        }

        user.updateLoginAttempts(0);
        userServiceShared.saveUser(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
        authenticationManager.authenticate(authenticationToken);
        return new LoginResponse(user.getUserId());
    }


    @Override
    public void forgotPassword(ResetPasswordRequest request) {
        User user = userServiceShared.getUserNameAndEmail(request.getUserName().toLowerCase(Locale.ROOT).replace(" ", ""), request.getEmail());
        if (user == null) {
            throw new AuthenticationFailedException("usuario no encontrado");
        }
        userServiceShared.saveUser(user);

        String json = "{'user_id':'" + user.getUserId() + "','user_name':'" + user.getUserName() + "', 'timestamp':'" + LocalDateTime.now().plusDays(1) + "'}";
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        String token = encryption.encrypt(jsonObject.toString(), null);
        String urlToken = "localhost" + "request.getUrlPath()" + "/reset_password/" + token;
        emailSendService.resetPasswordUser(user, urlToken);
    }

    @Override
    public void verifyTokenResetPassword(TokenResentPasswordRequest request) {
        if (!request.getPassword().equals(request.getPasswordConfirmed())) {
            throw new AuthenticationFailedException("tu contraseña no coincide.");
        }

        JsonObject token = new Gson().fromJson(encryption.decrypt(request.getToken(), null), JsonObject.class);

        UUID userId = UUID.fromString(token.get("user_id").getAsString());
        String userName = token.get("user_name").getAsString();
        LocalDateTime expirationDate = LocalDateTime.parse(token.get("timestamp").getAsString());

        User user = userServiceShared.getUserOfResetPassword(userId, userName);

        if (LocalDateTime.now().isAfter(expirationDate)) {
            throw new AuthenticationFailedException("El token ya ha expirado");
        }

        user.updatePassword(passwordEncoder.encode(request.getPassword()));
        user.updateLoginAttempts(0);
        user.updateLocked(false);
        userServiceShared.saveUser(user);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenShared.evaluateRefreshToken(request.getRefreshToken());
        User user = refreshToken.getUser();
        refreshTokenShared.deleteRefreshTokenByUser(user);
        return RefreshTokenResponse.create(refreshTokenShared.createRefreshToken(user), jwtTokenProvider.generateToken(user.getUserName()));
    }

    private void updateLockedUser(User user) {
        user.updateLocked(true);
        userServiceShared.saveUser(user);
        throw new AuthenticationFailedException("usuario bloqueado debido a intentos fallidos de inicio de sesión.");
    }

    private void updateLoginAttemptsUser(User user) {
        user.updateLoginAttempts(user.getLoginAttempts() + 1);
        userServiceShared.saveUser(user);
        throw new AuthenticationFailedException("quedan " + user.getLoginAttempts() + " intentos.");
    }
}
