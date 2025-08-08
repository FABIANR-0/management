package com.proyecto.demo.role.service;

import com.proyecto.demo.role.dto.RoleRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Override
    public void createRole(RoleRequest request) {
        System.out.println("llego al servicio");
    }
}
