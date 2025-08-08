package com.project.management.role.controller;

import com.project.management.permission.dto.PermissionDto;
import com.project.management.role.dto.CreateRoleRequest;
import com.project.management.role.dto.RoleAssignRequest;
import com.project.management.role.dto.RoleDto;
import com.project.management.role.dto.RoleResponseDto;
import com.project.management.role.service.RoleService;
import com.project.management.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Controlador del modúlo Role
 */
@Slf4j
@RestController
@RequestMapping("secure/role")
public class RoleController {

    private final RoleService roleService;

    /**
     * Constructor para el modúlo Role
     *
     * @param roleService Servicio del modúlo
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Crea un nuevo rol
     *
     * @param request el objeto de solicitud para crear el rol.
     * @return ResponseEntity con el estado HTTP 201 (Creado) en caso de éxito.
     */
    @PostMapping(value = "/")
    @Operation(security = @SecurityRequirement(name = "bearer-key") ,description = "Create Role Of user Authorized")
    @ApiResponse(responseCode = "201", description = "create")
    @PreAuthorize("hasPermission('HttpStatus','CREATE_ROLE')")
    public ResponseEntity<RoleDto> create(@RequestBody CreateRoleRequest request) {
        return new ResponseEntity<>(roleService.create(request) ,HttpStatus.CREATED);
    }

    /**
     * Asignar roles a un usuario
     *
     * @param request el objeto de solicitud para asignar los roles
     * @return ResponseEntity con el estado HTTP 200 (Ok) en caso de éxito.
     */
    @PostMapping(value = "/assign_role")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, description = "Assign role to user")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<HttpStatus> assignRoleToUser(@RequestBody RoleAssignRequest request) {
        roleService.assignRole(Arrays.asList(request.getRoles()), request.getUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * Actualiza permisos del rol
     *
     * @param request Lista de permisos que se asignaran al rol
     * @param roleId identíficador del rol al que se le actualizaran los permisos
     * @return  ResponseEntity con el estado HTTP 204 (no_content) en caso de éxito.
     */
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasPermission('HttpStatus','EDIT_ROLE')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, description = "Edit role")
    @ApiResponse(responseCode = "204", description = "no content")
    public ResponseEntity<HttpStatus> updatePermissions(@Valid @RequestBody List<PermissionDto>  request,
                                                        @Parameter(description = "UUID of an role", required = true) @PathVariable("id") UUID roleId) {
        roleService.updatePermissions(request,roleId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Quitar rol al usuario
     *
     * @param roleId identíficador del rol que será removido
     * @param userId identíficador del usuario que se le va a quitar el rol
     * @return ResponseEntity con el estado HTTP 200 (Ok) en caso de éxito.
     */
    @DeleteMapping(value = "/{userId}/{roleId}")
    @PreAuthorize("hasPermission('HttpStatus','DELETE_ROLE')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, description = "Remove role from user")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<HttpStatus> removeRoleFromUser(@Parameter(description = "UUID of an role",
            required = true) @PathVariable("roleId") UUID roleId, @Parameter(description = "UUID of an user", required = true) @PathVariable("userId") UUID userId) {
        roleService.removeRole(roleId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Eliminar rol
     *
     * @param roleId Identíficador del rol a eliminar
     * @return ResponseEntity con el estado HTTP 204 (no_content) en caso de éxito.
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasPermission('HttpStatus','DELETE_ROLE')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, description = "Delete role")
    @ApiResponse(responseCode = "204", description = "No Content")
    public ResponseEntity<HttpStatus> deleteRole(@Parameter(description = "UUID of an role", required = true) @PathVariable(value = "id") UUID roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtener  datos de un rol
     *
     * @param roleId Identíficador del rol a consultar
     * @return ResponseEntity con el rol y estado HTTP 200 (OK) en caso de éxito.
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasPermission('HttpStatus','GET_ROLE')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") },description = "Get role by roleId Of user Authorized")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<RoleDto> getRoleById(@Parameter(description = "UUID of an role", required = true) @PathVariable("id") UUID roleId) {
        return ResponseEntity.ok(roleService.findById(roleId));
    }

    /**
     * Obtener usuarios de un rol
     *
     * @param roleId Identíficador del rol por el cual se consultaran los usuarios
     * @return ResponseEntity con la lista de usuarios y estado HTTP 200 (OK) en caso de éxito.
     */
    @GetMapping(value = "/users/{roleId}")
    @PreAuthorize("hasPermission('HttpStatus','GET_ROLE')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, description = "Get users by role")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@Parameter(description = "UUID of an role", required = true) @PathVariable("roleId") UUID roleId) {
        return ResponseEntity.ok(roleService.getUsersByRole(roleId));
    }

    /**
     * Obtener los roles del comercio autenticado
     *
     * @return ResponseEntity con la lista de roles y estado HTTP 200 (OK) en caso de éxito.
     */
    @GetMapping(value = "/all/commerce")
    @PreAuthorize("hasPermission('HttpStatus','GET_ROLE')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") },description = "Get roles by commerce authenticated")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<List<RoleDto>> getRolesByUser() {
        return new ResponseEntity<>(roleService.getRolesByCommerce(), HttpStatus.OK);
    }

    /**
     * Obtener los roles del comercio autenticado
     *
     * @return ResponseEntity con la lista de roles y estado HTTP 200 (OK) en caso de éxito.
     */
    @GetMapping(value = "/all")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") },description = "Get all roles by commerce authenticated")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<List<RoleResponseDto>> getAllRolesByUser() {
        return new ResponseEntity<>(roleService.getAllRolesByCommerce(), HttpStatus.OK);
    }

    /**
     * Verifica si existe un rol por su nombre.
     *
     * @param name Nombre del rol a verificar.
     * @return ResponseEntity con un booleano que indica si el rol existe y estado HTTP 200 (OK) en caso de éxito.
     */
    @GetMapping(value = "/exists_by_name/{name}")
    @Operation(description = "Get role exists by name")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<Boolean> getExistsRoleByName(@Parameter(description = "Name of an role", required = true) @PathVariable("name") String name) {
        return new ResponseEntity<>(roleService.existsByName(name), HttpStatus.OK);
    }
}
