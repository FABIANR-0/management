package com.proyecto.demo.role.controller;

import com.proyecto.demo.role.dto.RoleRequest;
import com.proyecto.demo.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") },description = "Create Role Of user Authorized")
    @ApiResponse(responseCode = "201",description = "Created")
    @PreAuthorize("hasPermission('HttpStatus','CREATE_ROLE')")
    public ResponseEntity<HttpStatus> createRole(@Valid @RequestBody RoleRequest request){
        roleService.createRole(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
