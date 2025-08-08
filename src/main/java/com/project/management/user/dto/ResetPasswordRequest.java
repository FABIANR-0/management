package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ResetPasswordRequest {

    @NotNull
    @NotEmpty
    @JsonProperty(value = "token")
    private String token;

    @NotNull
    @NotEmpty
    @Size(max = 30, min = 8)
    @JsonProperty(value = "user_password")
    private String password;

    @NotNull
    @NotEmpty
    @Size(max = 30, min = 8)
    @JsonProperty(value = "user_password_confirmed")
    private String passwordConfirmed;
}
