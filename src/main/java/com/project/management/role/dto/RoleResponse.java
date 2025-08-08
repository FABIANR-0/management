package com.project.management.role.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RoleResponse {

    @JsonProperty(value = "role_id")
    private UUID roleId;

    @JsonProperty(value = "role_name")
    private String roleName;

    public RoleResponse(UUID roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public static RoleResponse response(UUID roleId, String roleName){
        return new RoleResponse(roleId, roleName);
    }
}
