package com.project.management.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectResponse {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "image_base64")
    private String imageBase64;

    @JsonProperty(value = "person_name")
    private String personName;

    public ProjectResponse(String name, String description, String imageBase64, String personName) {
        this.name = name;
        this.description = description;
        this.imageBase64 = imageBase64;
        this.personName = personName;
    }
}
