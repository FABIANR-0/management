package com.project.management.module.service.impl;

import com.project.management.module.dto.UserModulesResponse;
import com.project.management.module.repository.ModuleCustomRepository;
import com.project.management.module.service.ModuleServiceShard;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceSharedImpl implements ModuleServiceShard {

    private final ModuleCustomRepository moduleCustomRepository;

    public ModuleServiceSharedImpl(ModuleCustomRepository moduleCustomRepository) {
        this.moduleCustomRepository = moduleCustomRepository;
    }

    @Override
    public List<UserModulesResponse> getModuleOfUser(UUID userId) {
        return moduleCustomRepository.getModuleOfUser(userId);
    }
}
