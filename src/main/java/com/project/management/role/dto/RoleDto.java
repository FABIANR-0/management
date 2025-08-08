package com.project.management.role.dto;

import com.project.management.permission.dto.PermissionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class RoleDto {
    @JsonProperty(value = "role_id", access = JsonProperty.Access.READ_ONLY)
    private UUID roleId;

    @JsonProperty(value = "role_name")
    @NotBlank
    @Size(min = 1, max = 100)
    private String roleName;

    @JsonProperty(value = "role_active", access = JsonProperty.Access.READ_ONLY)
    private Boolean roleActive;


    @JsonProperty(value = "permissions", access = JsonProperty.Access.READ_ONLY)
    private List<PermissionDto> permissions;

    @JsonProperty(value = "count_usuer")
    private Long countUser;

    public RoleDto(UUID roleId, String roleName, Boolean roleActive, Long countUser, List<PermissionDto> permissions) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleActive = roleActive;
        this.countUser = countUser;
        this.permissions = permissions;
    }

    public static RoleDto create(UUID roleId, String roleName, Boolean roleActive, Long countUser, List<PermissionDto> permissions) {
        return new RoleDto(
                roleId,
                roleName,
                roleActive,
                countUser,
                permissions
        );
    }

    public RoleDto(UUID roleId, String roleName, Boolean roleActive, Long countUser) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleActive = roleActive;
        this.countUser = countUser;
    }

    public static RoleDto create(UUID roleId, String roleName, Boolean roleActive,  Long countUser) {
        return new RoleDto(
                roleId,
                roleName,
                roleActive,
                countUser
        );
    }
}
