package com.project.management.module.repository;

import com.project.management.module.dto.UserModulesResponse;

import java.util.List;
import java.util.UUID;

public interface ModuleCustomRepository {

    List<UserModulesResponse> getModuleOfUser(UUID userId);
}
