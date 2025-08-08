package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserAuthorized {
    @JsonProperty(value = "total_users")
    private Long totalUsers;

    @JsonProperty(value = "users")
    private List<UsersAuthorizedResponse> usersAuthorizedResponses;

    public UserAuthorized(Long totalUsers, List<UsersAuthorizedResponse> usersAuthorizedResponses) {
        this.totalUsers = totalUsers;
        this.usersAuthorizedResponses = usersAuthorizedResponses;
    }
}
