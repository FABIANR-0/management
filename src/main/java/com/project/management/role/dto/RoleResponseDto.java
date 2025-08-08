package com.project.management.role.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RoleResponseDto {
    @JsonProperty(value = "role_id", access = JsonProperty.Access.READ_ONLY)
    private UUID roleId;

    @JsonProperty(value = "role_name")
    @NotBlank
    @Size(min = 1, max = 100)
    private String roleName;


    public RoleResponseDto(UUID roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public static RoleResponseDto create(UUID roleId, String roleName) {
        return new RoleResponseDto(
                roleId,
                roleName
        );
    }
}
