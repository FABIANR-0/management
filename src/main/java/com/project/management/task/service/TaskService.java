package com.project.management.task.service;

import com.project.management.task.dto.TaskCreate;

public interface TaskService {

    void createTask(TaskCreate request);
}
