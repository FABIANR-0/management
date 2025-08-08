package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DoubleFactorAuthenticationRequest {

    @NotNull
    @JsonProperty(value = "user_mfa_email")
    private Boolean userMfaEmail;

}
