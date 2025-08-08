package com.project.management.task.service.impl;

import com.project.management.project.service.ProjectService;
import com.project.management.task.dto.TaskCreate;
import com.project.management.task.entity.Task;
import com.project.management.task.repository.TaskRepository;
import com.project.management.task.service.TaskService;
import com.project.management.user.service.UserServiceShared;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserServiceShared userServiceShared;
    private final ProjectService projectService;

    @Override
    public void createTask(TaskCreate request) {

        if (taskRepository.existsByNameAndProject_ProjectId(request.getName(), request.getProjectId())) {
            throw new IllegalArgumentException(String.format("Tarea con el nombre %s ya existe en el proyecto %s", request.getName(), request.getProjectId()));
        }

        taskRepository.save(
                Task.create(
                        projectService.getProjectById(request.getProjectId()),
                        null, //todo - list of task
                        userServiceShared.getUserById(request.getUserIdAssigned()),
                        request.getName(),
                        request.getDescription(),
                        null //todo - tags of task
                )
        );
    }
}
