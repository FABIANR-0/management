package com.project.management.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModulesResponse {

    @JsonProperty(value = "module_name",required = true)
    private String name;

    @JsonProperty(value = "ico",required = true)
    private String ico;

    @JsonProperty(value = "description",required = true)
    private String description;

    @JsonProperty(value = "route",required = true)
    private String route;

    @JsonProperty(value = "order",required = true)
    private int order;

    public UserModulesResponse() {
    }

    public UserModulesResponse(String name, String ico, String description, String route, int order) {
        this.name = name;
        this.ico = ico;
        this.description = description;
        this.route = route;
        this.order = order;
    }
}
