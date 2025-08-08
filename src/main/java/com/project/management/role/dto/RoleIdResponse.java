package com.project.management.role.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RoleIdResponse {

    @JsonProperty(value = "role_id")
    private UUID roleId;

    public RoleIdResponse() {
        this.roleId = null;
    }

    public RoleIdResponse(UUID roleId) {
        this.roleId = roleId;
    }

    public UUID roleId() {
        return roleId;
    }

    public static RoleIdResponse response(UUID roleId){
        return new RoleIdResponse(roleId);
    }
}
