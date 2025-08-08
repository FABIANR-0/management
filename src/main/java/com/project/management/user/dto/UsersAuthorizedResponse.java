package com.project.management.user.dto;


import com.project.management.user.status.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class UsersAuthorizedResponse {

    @JsonProperty(value = "user_id")
    private UUID userId;

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "user_status")
    private String status;

    @JsonProperty(value = "user_image")
    private String userImage;

    @JsonProperty(value = "last_login")
    private LocalDateTime lastLogin;

    @JsonProperty(value = "charge")
    private String charge;


    public UsersAuthorizedResponse(UUID userId, String userName, UserStatus userStatus, String userImage ,LocalDateTime lastLogin, String charge) {
        this.userId = userId;
        this.userName = userName;
        this.status = userStatus.getValue();
        this.userImage = userImage;
        this.lastLogin = lastLogin;
        this.charge = charge;
    }
}
