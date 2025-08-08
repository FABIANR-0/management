package com.project.management.module.service;

import com.project.management.module.dto.UserModulesResponse;

import java.util.List;
import java.util.UUID;

public interface ModuleServiceShard {

    List<UserModulesResponse> getModuleOfUser(UUID userId);
}
