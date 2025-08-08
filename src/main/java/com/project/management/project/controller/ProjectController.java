package com.project.management.project.controller;

import com.project.management.project.dto.ProjectCreate;
import com.project.management.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("security/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/")
    @Operation(description = "Create Role Of user Authorized")
    @ApiResponse(responseCode = "201",description = "Created")
//    @PreAuthorize("hasPermission('HttpStatus','CREATE_PROJECT')")
    public ResponseEntity<HttpStatus> createProject(@Valid @RequestBody ProjectCreate request){
        projectService.createProject(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
