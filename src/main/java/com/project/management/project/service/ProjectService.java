package com.project.management.project.service;

import com.project.management.project.dto.ProjectCreate;
import com.project.management.project.dto.ProjectResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface ProjectService {

    void createProject(@Valid ProjectCreate request);

    List<ProjectResponse> getAllProjects();
}
