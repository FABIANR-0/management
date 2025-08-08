package com.project.management.project.service.impl;

import com.project.management.authentication.service.AuthenticationService;
import com.project.management.project.dto.ProjectCreate;
import com.project.management.project.dto.ProjectResponse;
import com.project.management.project.entity.Project;
import com.project.management.project.repository.ProjectRepository;
import com.project.management.project.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final AuthenticationService authenticationService;
    @Override
    public void createProject(ProjectCreate request) {
        if(projectRepository.existsByName(request.getName())) {
            log.error("Nombre de proyecto ya existe: {}", request.getName());
            throw new IllegalArgumentException(String.format("Nombre de proyecto ya existe: %s", request.getName()));
        }
        projectRepository.save(Project.create(request.getName(), request.getDescription(), request.getImageBase64(), authenticationService.getUserAuthenticated()));
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.getAllProjects();
    }
}
