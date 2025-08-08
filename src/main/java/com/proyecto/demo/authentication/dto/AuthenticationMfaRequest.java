package com.proyecto.demo.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
public class AuthenticationMfaRequest {

    @NonNull
    @JsonProperty(value = "user_id",required = true)
    private UUID userId;

    @NotBlank
    @JsonProperty(value = "code",required = true)
    @Size(min = 6 ,max = 6)
    private String code;

}
