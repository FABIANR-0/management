package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UserResponseDto {
    @JsonProperty(value = "user_mfa_email")
    private Boolean userMfaEmail;

    @JsonProperty(value = "user_secret_key")
    private String userSecretKey;

    @JsonProperty(value = "user_qr_authenticator")
    private String qrAuthenticator;

    public UserResponseDto(Boolean userMfaEmail, String userSecretKey, String qrAuthenticator) {
        this.userMfaEmail = userMfaEmail;
        this.userSecretKey = userSecretKey;
        this.qrAuthenticator = qrAuthenticator;
    }

    public static UserResponseDto create(Boolean userMfaEmail, String userSecretKey, String qrAuthenticator) {
        return new UserResponseDto(
               userMfaEmail,
                userSecretKey,
                qrAuthenticator
        );
    }
}
