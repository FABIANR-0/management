package com.project.management.task.repository;

import com.project.management.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Boolean existsByNameAndProject_ProjectId(String name, UUID projectId);
}
