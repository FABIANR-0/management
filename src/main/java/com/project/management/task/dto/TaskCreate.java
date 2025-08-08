package com.project.management.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TaskCreate {

    @NotBlank
    @JsonProperty(value = "name")
    private String name;

    @NotBlank
    @JsonProperty(value = "description")
    private String description;

    @NotNull
    @JsonProperty(value = "user_id_assigned")
    private UUID userIdAssigned;

    @NotNull
    @JsonProperty(value = "project_id")
    private UUID projectId;

    @NotNull
    @JsonProperty(value = "list_id")
    private UUID listId;
}
