package com.project.management.project.repository;

import com.project.management.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Boolean existsByName(String name);
}
