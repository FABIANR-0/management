package com.project.management.role.service;

import com.project.management.permission.dto.PermissionDto;
import com.project.management.role.dto.CreateRoleRequest;
import com.project.management.role.dto.RoleDto;
import com.project.management.role.dto.RoleResponseDto;
import com.project.management.role.entity.Role;
import com.project.management.user.dto.UserResponse;


import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleDto findById(UUID roleId);

    Boolean existsByName(String name);

    List<RoleDto> getRolesByCommerce();

    List<UserResponse> getUsersByRole(UUID roleId);

    RoleDto create(CreateRoleRequest request);

    void updatePermissions(List<PermissionDto> request, UUID roleId);

    void assignRole(List<UUID> idsRole, UUID userId);

    void removeRole(UUID roleId, UUID userId);

    void deleteRole(UUID roleId);

    List<RoleResponseDto> getAllRolesByCommerce();

    List<Role> getRolesByIds(List<UUID> rolesIds);

    List<Role> getRolesOfMicrosoft(String[] roleName);

}
