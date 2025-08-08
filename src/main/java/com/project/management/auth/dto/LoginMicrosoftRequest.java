package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginMicrosoftRequest {
    @NotBlank
    @JsonProperty(value = "access_token", required = true)
    private String accessToken;

    @Size(min = 1)
    @JsonProperty(value = "roles", required = true)
    private String[] roles;
}
