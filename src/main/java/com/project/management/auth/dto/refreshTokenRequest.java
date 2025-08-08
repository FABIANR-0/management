package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class refreshTokenRequest {

    @NotNull
    @NotEmpty
    @JsonProperty(value = "refresh_token_id")
    private String refreshTokenId;
}
