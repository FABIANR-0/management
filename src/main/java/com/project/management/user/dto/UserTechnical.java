package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserTechnical {

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "person_name")
    private String name;

    @JsonProperty(value = "person_lastname")
    private String lastname;

    public UserTechnical() {}

    public UserTechnical(UUID userId, String userName, String name, String lastname) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.lastname = lastname;
    }
}
