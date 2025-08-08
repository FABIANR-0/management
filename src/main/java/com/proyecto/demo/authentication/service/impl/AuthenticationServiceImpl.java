package com.proyecto.demo.authentication.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.proyecto.demo.authentication.dto.*;
import com.proyecto.demo.authentication.service.AuthenticationService;
import com.proyecto.demo.common.encryption.Encryption;
import com.proyecto.demo.common.exception.service.AuthenticationFailedException;
import com.proyecto.demo.email.service.EmailSendService;
import com.proyecto.demo.module.service.ModuleServiceShard;
import com.proyecto.demo.refreshToken.dto.RefreshTokenRequest;
import com.proyecto.demo.refreshToken.dto.RefreshTokenResponse;
import com.proyecto.demo.refreshToken.entity.RefreshToken;
import com.proyecto.demo.refreshToken.service.RefreshTokenShared;
import com.proyecto.demo.security.jwt.JwtTokenProvider;
import com.proyecto.demo.user.entity.User;
import com.proyecto.demo.user.service.UserServiceShared;
import com.proyecto.demo.user.util.GenericLoginAttempts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceShared userServiceShared;
    private final PasswordEncoder passwordEncoder;
    private final EmailSendService emailSendService;
    private final ModuleServiceShard moduleServiceShard;
    private final Encryption encryption;
    private final RefreshTokenShared refreshTokenShared;
    @Value("${settings.auth.time-resent-email}")
    private int timeResentEmail;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserServiceShared userServiceShared, PasswordEncoder passwordEncoder, EmailSendService emailSendService, ModuleServiceShard moduleServiceShard, Encryption encryption, RefreshTokenShared refreshTokenShared) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userServiceShared = userServiceShared;
        this.passwordEncoder = passwordEncoder;
        this.emailSendService = emailSendService;
        this.moduleServiceShard = moduleServiceShard;
        this.encryption = encryption;
        this.refreshTokenShared = refreshTokenShared;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userServiceShared.getUserByUserName(request.getUserName().toLowerCase(Locale.ROOT).replace(" ",""));

        if (user.isLocked()){
            throw new AuthenticationFailedException("el usuario se encuentra bloqueado");
        }

        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            if (user.getLoginAttempts()>=2){
                this.updateLockedUser(user);
            }else {
                this.updateLoginAttemptsUser(user,GenericLoginAttempts.LOGIN_ATTEMPTS);
            }
        }

        user.updateLoginAttempts(0);
        user.addCodeVerification(this.generateCodeVerification(user));
        user.updateQuantityResentEmail(0);
        userServiceShared.saveUser(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
        authenticationManager.authenticate(authenticationToken);
        return new LoginResponse(user.getUserId());
    }

    @Override
    public LoginResponse authenticationMfa(AuthenticationMfaRequest request) {
        User user = userServiceShared.getUserById(request.getUserId());

        if (user.isLocked()){
            throw new AuthenticationFailedException("el usuario se encuentra bloqueado");
        }

        if (user.getCreateCodeVerification() == null || !Objects.equals(user.getCodeVerification(), request.getCode())){
            this.updateLoginAttemptsUser(user,GenericLoginAttempts.LOGIN_ATTEMPTS_MFA);
        }

        Duration duration = Duration.between(user.getCreateCodeVerification(), LocalDateTime.now());
        if (duration.toMinutes() > 5){
            throw new AuthenticationFailedException("el código ha expirado, vuelve a iniciar sesión");
        }

        if (user.getRefreshToken() != null){
            refreshTokenShared.deleteRefreshTokenByUser(user);
        }
        user.updateLoginAttemptsMfa(0);
        user.resetCodeVerification();
        user.updateQuantityResentEmail(0);
        userServiceShared.saveUser(user);
        return new LoginResponse(jwtTokenProvider.generateToken(user.getUserName()), refreshTokenShared.createRefreshToken(user),userServiceShared.getInfoBasicUser(request.getUserId()),moduleServiceShard.getModuleOfUser(request.getUserId()),userServiceShared.getAllPermission(user.getUserName()));
    }

    @Override
    public void resentEmailByUser(UUID userId) {
        User user = userServiceShared.getUserById(userId);
        user.addCodeVerification(this.generateCodeVerification(user));
        user.updateQuantityResentEmail(user.getQuantityResentEmail()+1);
        userServiceShared.saveUser(user);
    }

    @Override
    public void forgotPassword(ResetPasswordRequest request) {
        User user = userServiceShared.getUserNameAndEmail(request.getUserName().toLowerCase(Locale.ROOT).replace(" ",""), request.getEmail());
        if (user == null){
            throw new AuthenticationFailedException("usuario no encontrado");
        }
        user.updateResetPasswordUpdateAt(null);
        userServiceShared.saveUser(user);

        String json = "{'user_id':'" + user.getUserId() + "','user_name':'" + user.getUserName() + "', 'timestamp':'" + LocalDateTime.now().plusDays(1) + "'}";
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        String token = encryption.encrypt(jsonObject.toString(), null);
        String urlToken = "localhost" + "request.getUrlPath()" + "/reset_password/" + token;
        emailSendService.resetPasswordUser(user,urlToken);
    }

    @Override
    public void verifyTokenResetPassword(TokenResentPasswordRequest request) {
        if (!request.getPassword().equals(request.getPasswordConfirmed())){
            throw new AuthenticationFailedException("tu contraseña no coincide.");
        }

        JsonObject token = new Gson().fromJson(encryption.decrypt(request.getToken(),null), JsonObject.class);

        UUID userId = UUID.fromString(token.get("user_id").getAsString());
        String userName = token.get("user_name").getAsString();
        LocalDateTime expirationDate = LocalDateTime.parse(token.get("timestamp").getAsString());

        User user = userServiceShared.getUserOfResetPassword(userId,userName);

        if (user.getResetPasswordUpdateAt() != null){
            throw new AuthenticationFailedException("el token ya fue utilizado");
        }

        if (LocalDateTime.now().isAfter(expirationDate)) {
            throw new AuthenticationFailedException("El token ya ha expirado");
        }

        user.updatePassword(passwordEncoder.encode(request.getPassword()));
        user.updateResetPasswordUpdateAt(LocalDateTime.now());
        user.updateLoginAttempts(0);
        user.updateLoginAttemptsMfa(0);
        user.updateQuantityResentEmail(0);
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

    private void updateLockedUser(User user){
        user.updateLocked(true);
        userServiceShared.saveUser(user);
        throw new AuthenticationFailedException("usuario bloqueado debido a intentos fallidos de inicio de sesión.");
    }

    private void updateLoginAttemptsUser(User user, GenericLoginAttempts attempts){
        if (GenericLoginAttempts.LOGIN_ATTEMPTS.equals(attempts)){
            user.updateLoginAttempts(user.getLoginAttempts() + 1);
        }else {
            if (user.getLoginAttemptsMfa()>=2){
                this.updateLockedUser(user);
            }
            user.updateLoginAttemptsMfa(user.getLoginAttemptsMfa()+1);
        }
        userServiceShared.saveUser(user);
        String message = (attempts.equals(GenericLoginAttempts.LOGIN_ATTEMPTS))?".":" el código es inválido";
        throw new AuthenticationFailedException("quedan "+(attempts.equals(GenericLoginAttempts.LOGIN_ATTEMPTS)?3-user.getLoginAttempts():3-user.getLoginAttemptsMfa())+" intentos"+message);
    }

    private String generateCodeVerification(User user){
        if (user.getCreateCodeVerification()!= null){
            Duration duration = Duration.between(user.getCreateCodeVerification(),LocalDateTime.now());
            if (user.getQuantityResentEmail()>=3 && duration.toMinutes() <= timeResentEmail){
                throw new AuthenticationFailedException("has alcanzado el límite de intentos para reenviar el código a tu correo, reintenta en "+(timeResentEmail-duration.toMinutes())+" minutos.");
            }
        }
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(999999);
        String codeVerification = String.format("%06d",code);
        emailSendService.emailCodeVerification(codeVerification);
        return codeVerification;
    }
}
