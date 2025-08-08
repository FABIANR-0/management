package com.project.management.module.service;

import com.project.management.module.dto.ModuleResponse;
import com.project.management.module.entity.Module;
import com.project.management.module.repository.ModuleRepository;
import com.project.management.permission.dto.PermissionDto;
import com.project.management.permission.entity.Permission;
import com.project.management.permission.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Servicio para el manejo de los módulos.
 */
@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    private final PermissionRepository permissionRepository;

    public ModuleService(ModuleRepository moduleRepository, PermissionRepository permissionRepository) {
        this.moduleRepository = moduleRepository;
        this.permissionRepository = permissionRepository;
    }

    /**
     * Obtiene los módulos y permisos de un rol en específico
     *
     * @param roleId id del rol por el cual se buscaran los permisos
     * @return Lista de módulos con sus respectivos permisos
     */
    @Transactional
    public List<ModuleResponse> getAllModules(UUID roleId) {
        List<Permission> permissions = permissionRepository.findAllByRoles_RoleId(roleId);
        List<Module> modules = moduleRepository.findAll();
        List<ModuleResponse> moduleResponses = new ArrayList<>();
        modules.forEach(module -> {
            List<PermissionDto> permissionDtos = new ArrayList<>();
            module.getPermissions().forEach(permission -> {
                if(!permission.getPermissionId().equals(UUID.fromString("77d6cac9-4bce-49af-9d1f-99a5ac37937e"))){
                    permission.setChecked(permissions.contains(permission));
                    permissionDtos.add(PermissionDto.create(permission.getPermissionId(), permission.getName(), permission.getTitle(), permission.getChecked()));
                }
            });
            moduleResponses.add(ModuleResponse.response(module.getModuleId(), module.getParentId(), module.getName(), module.getDescription(), module.getRoute(), module.getOrder(), permissionDtos, module.getPrimaryIcon(), module.getSecondaryIcon(), module.getTertiaryIcon()));
        });

        return moduleResponses;
    }
}
