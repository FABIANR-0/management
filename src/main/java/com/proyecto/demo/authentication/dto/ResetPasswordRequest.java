package com.proyecto.demo.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ResetPasswordRequest {

    @NotBlank
    @JsonProperty(value = "user_name",required = true)
    private String userName;

    @NotBlank
    @Email
    @JsonProperty(value = "email",required = true)
    private String email;
}
