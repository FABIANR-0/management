package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {

    @NotBlank
    @JsonProperty(value = "user_current_password")
    private String currentPassword;

    @NotBlank
    @Size(max = 30, min = 8)
    @JsonProperty(value = "user_password")
    private String password;

    @NotBlank
    @Size(max = 30, min = 8)
    @JsonProperty(value = "user_password_confirmed")
    private String passwordConfirmed;
}
