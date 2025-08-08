package com.proyecto.demo.module.repository;

import com.proyecto.demo.module.dto.UserModulesResponse;

import java.util.List;
import java.util.UUID;

public interface ModuleCustomRepository {

    List<UserModulesResponse> getModuleOfUser(UUID userId);
}
