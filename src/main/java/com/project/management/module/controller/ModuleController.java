package com.project.management.module.controller;

import com.project.management.module.dto.ModuleResponse;
import com.project.management.module.service.ModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Controlador para el módulo.
 */
@Slf4j
@RestController
@RequestMapping("secure/module")
public class ModuleController {

    private final ModuleService moduleService;

    /**
     * Constructor de la clase ModuleController.
     *
     * @param moduleService Servicio del módulo.
     */
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    /**
     * Obtiene los módulos y permisos de un rol en específico
     *
     * @param roleId id del rol por el cual se buscaran los permisos
     * @return Lista de módulos con sus respectivos permisos
     */
    @GetMapping(value = "/role/{id}")
    @Operation(description = "Get all modules with an Role ID")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<List<ModuleResponse>> getAllByRoleId(@Parameter(description = "UUID of an role", required = true) @PathVariable("id") UUID roleId) {
        return new ResponseEntity<>(moduleService.getAllModules(roleId), HttpStatus.OK);
    }


}
