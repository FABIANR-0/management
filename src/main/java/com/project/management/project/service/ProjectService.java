package com.project.management.project.service;

import com.project.management.project.dto.ProjectCreate;
import com.project.management.project.dto.ProjectResponse;
import com.project.management.project.entity.Project;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    void createProject(@Valid ProjectCreate request);

    List<ProjectResponse> getAllProjects();

    Project getProjectById(UUID projectId);
}
