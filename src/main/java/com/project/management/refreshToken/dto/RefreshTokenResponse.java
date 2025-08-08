package com.project.management.refreshToken.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RefreshTokenResponse {

    @JsonProperty(value = "refresh_token",required = true)
    private UUID refreshToken;

    @JsonProperty(value = "access_token",required = true)
    private String accessToken;

    @JsonProperty(value = "token_type",required = true)
    private String tokenType;


    public RefreshTokenResponse(UUID refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }

    public static RefreshTokenResponse create(UUID refreshToken, String accessToken){
        return new RefreshTokenResponse(refreshToken,accessToken);
    }
}
