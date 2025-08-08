package com.project.management.role.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RoleAssignRequest {

    @NotBlank
    @JsonProperty(value = "user_id")
    private UUID userId;

    @Size(min = 1)
    @JsonProperty(value = "roles", required = true)
    private UUID[] roles;
}
