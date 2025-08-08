package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ForgotPasswordRequest {

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "person_email")
    private String email;
}
