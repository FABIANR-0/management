package com.proyecto.demo.module.service;

import com.proyecto.demo.module.dto.UserModulesResponse;

import java.util.List;
import java.util.UUID;

public interface ModuleServiceShard {

    List<UserModulesResponse> getModuleOfUser(UUID userId);
}
