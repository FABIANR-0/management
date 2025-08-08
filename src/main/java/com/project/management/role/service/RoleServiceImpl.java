package com.project.management.role.service;

import com.project.management.role.dto.RoleRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Override
    public void createRole(RoleRequest request) {
        System.out.println("llego al servicio");
    }
}
