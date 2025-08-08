package com.project.management.project.service.impl;

import com.project.management.project.dto.ProjectCreate;
import com.project.management.project.entity.Project;
import com.project.management.project.repository.ProjectRepository;
import com.project.management.project.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public void createProject(ProjectCreate request) {
        if(projectRepository.existsByName(request.getName())) {
            log.error("Nombre de proyecto ya existe: {}", request.getName());
            throw new IllegalArgumentException(String.format("Nombre de proyecto ya existe: %s", request.getName()));
        }
        projectRepository.save(Project.create(request.getName(), request.getDescription(), request.getImageBase64()));
    }
}
