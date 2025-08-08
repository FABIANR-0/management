package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PersonEmail {

    @NotNull
    @JsonProperty(value = "person_email")
    private String email;

    public PersonEmail(String email) {
        this.email = email;
    }

    static public PersonEmail response(String email) {
        return new PersonEmail(
                email
        );
    }
}
