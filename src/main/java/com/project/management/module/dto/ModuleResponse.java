package com.project.management.module.dto;

import com.project.management.permission.dto.PermissionDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * DTO para la respuesta de un módulo.
 */
public class ModuleResponse {

    @JsonProperty(value = "module_id", access = JsonProperty.Access.READ_ONLY)
    private UUID moduleId;

    @JsonProperty(value = "parent_id")
    private UUID parentId;

    @JsonProperty(value = "module_name")
    private String moduleName;

    @JsonProperty(value = "module_description")
    private String moduleDescription;

    @JsonProperty(value = "module_route")
    private String moduleRoute;

    @JsonProperty(value = "module_order")
    private Integer moduleOrder;

    @JsonProperty(value = "permissions")
    private List<PermissionDto> permissionDtos;

    @JsonProperty(value = "module_primary_icon")
    private String modulePrimaryIcon;

    @JsonProperty(value = "module_secondary_icon")
    private String moduleSecondaryIcon;

    @JsonProperty(value = "module_tertiary_icon")
    private String moduleTertiaryIcon;

    public ModuleResponse(UUID moduleId, UUID parentId, String moduleName, String moduleDescription, String moduleRoute, Integer moduleOrder, List<PermissionDto> permissionDtos, String modulePrimaryIcon, String moduleSecondaryIcon, String moduleTertiaryIcon) {
        this.moduleId = moduleId;
        this.parentId = parentId;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.moduleRoute = moduleRoute;
        this.moduleOrder = moduleOrder;
        this.permissionDtos = permissionDtos;
        this.modulePrimaryIcon = modulePrimaryIcon;
        this.moduleSecondaryIcon = moduleSecondaryIcon;
        this.moduleTertiaryIcon = moduleTertiaryIcon;
    }

    public static ModuleResponse response(UUID moduleId, UUID parentId, String moduleName, String moduleDescription, String moduleRoute, Integer moduleOrder, List<PermissionDto> permissionDtos, String modulePrimaryIcon, String moduleSecondaryIcon, String moduleTertiaryIcon) {
        return new ModuleResponse(
                moduleId,
                parentId,
                moduleName,
                moduleDescription,
                moduleRoute,
                moduleOrder,
                permissionDtos,
                modulePrimaryIcon,
                moduleSecondaryIcon,
                moduleTertiaryIcon
        );
    }

}
