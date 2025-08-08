package com.project.management.refreshToken.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RefreshTokenRequest {

    @NotNull
    @JsonProperty(value = "refresh_token",required = true)
    private UUID refreshToken;
}
