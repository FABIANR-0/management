package com.project.management.role.repository;


import com.project.management.role.dto.RoleResponseDto;

import java.util.List;

public interface RoleRepositoryCustom {

    List<RoleResponseDto> getAllRolesByCommerce();
}
