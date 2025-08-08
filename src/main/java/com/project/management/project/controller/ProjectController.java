package com.project.management.project.controller;

import com.project.management.project.dto.ProjectCreate;
import com.project.management.project.dto.ProjectResponse;
import com.project.management.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("security/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/")
    @Operation(description = "Create project")
    @ApiResponse(responseCode = "201",description = "Created")
//    @PreAuthorize("hasPermission('HttpStatus','CREATE_PROJECT')")
    public ResponseEntity<HttpStatus> createProject(@Valid @RequestBody ProjectCreate request){
        projectService.createProject(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @Operation(description = "get all projects")
    @ApiResponse(responseCode = "200",description = "Success")
//    @PreAuthorize("hasPermission('HttpStatus','GET_PROJECT')")
    public ResponseEntity<List<ProjectResponse>> getAllProjects(){
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }
}
