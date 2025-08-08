package com.project.management.role.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateRoleRequest {

    @NotBlank
    @JsonProperty(value = "role_name")
    private String nameRole;
}
