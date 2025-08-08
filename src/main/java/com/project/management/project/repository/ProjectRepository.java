package com.project.management.project.repository;

import com.project.management.project.dto.ProjectResponse;
import com.project.management.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Boolean existsByName(String name);

    @Query("SELECT new com.project.management.project.dto.ProjectResponse(p.name, p.description, p.imageBase64, pe.name) " +
            "FROM Project p inner join User u on p.user.userId = u.userId inner join Person pe on pe.user.userId = u.userId" )
    List<ProjectResponse> getAllProjects();
}
