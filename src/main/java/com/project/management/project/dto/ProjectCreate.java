package com.project.management.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProjectCreate {

    @NotBlank
    @JsonProperty(value = "name")
    private String name;

    @NotBlank
    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "image_base64")
    private String imageBase64;
}
