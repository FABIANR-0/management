package com.proyecto.demo.permission.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PermissionResponse {
    @JsonProperty(value = "permission_name",required = true)
    private String permissionName;

    public PermissionResponse(String permissionName) {
        this.permissionName = permissionName;
    }
}
