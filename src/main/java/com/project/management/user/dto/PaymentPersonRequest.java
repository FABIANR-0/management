package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PaymentPersonRequest {
    @JsonProperty(value = "person_name")
    @Size(max = 60)
    private String name;

    @JsonProperty(value = "person_lastname")
    @Size(max = 60)
    private String lastname;

    @JsonProperty(value = "person_email")
    @Size(max = 100)
    private String email;

    @JsonProperty(value = "person_phone")
    @Size(max = 15, min = 8)
    private String phone;

    public PaymentPersonRequest(String name, String lastname, String email, String phone) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    public static PaymentPersonRequest create(String name, String lastname, String email, String phone){
        return new PaymentPersonRequest(
                name,
                lastname,
                email,
                phone
        );
    }
}
