package com.project.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class AuthModule {
    @JsonProperty(value = "module_id")
    private UUID moduleId;

    @JsonProperty(value = "parent_id")
    private UUID parentId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "module_route")
    private String route;

    @JsonProperty(value = "module_order")
    private Integer order;

    @JsonProperty(value = "primary_icon")
    private String primaryIcon;

    @JsonProperty(value = "secondary_icon")
    private String secondaryIcon;

    @JsonProperty(value = "tertiary_icon")
    private String tertiaryIcon;

    @JsonProperty(value = "submodules")
    private List<AuthModule> submodules;

    public AuthModule(UUID moduleId, UUID parentId, String name, String description, String route, Integer order, String primaryIcon, String secondaryIcon, String tertiaryIcon) {
        this.moduleId = moduleId;
        this.parentId = parentId;
        this.name = name;
        this.description = description;
        this.route = route;
        this.order = order;
        this.primaryIcon = primaryIcon;
        this.secondaryIcon = secondaryIcon;
        this.tertiaryIcon = tertiaryIcon;
    }

    public void addSubmodules(List<AuthModule> submodules){
        this.submodules = submodules;
    }
}
