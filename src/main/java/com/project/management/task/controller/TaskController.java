package com.project.management.task.controller;

import com.project.management.task.dto.TaskCreate;
import com.project.management.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/")
    @Operation(description = "Create task")
    @ApiResponse(responseCode = "201",description = "Created")
//    @PreAuthorize("hasPermission('HttpStatus','CREATE_TASK')")
    public ResponseEntity<HttpStatus> createTask(@Valid @RequestBody TaskCreate request){
        taskService.createTask(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
