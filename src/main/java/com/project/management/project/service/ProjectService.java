package com.project.management.project.service;

import com.project.management.project.dto.ProjectCreate;
import jakarta.validation.Valid;

public interface ProjectService {

    void createProject(@Valid ProjectCreate request);
}
