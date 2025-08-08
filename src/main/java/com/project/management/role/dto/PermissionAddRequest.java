package com.project.management.role.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class PermissionAddRequest {

    @JsonProperty(value = "permissions")
    private List<UUID> permissions;
}
