package com.proyecto.demo.module.service.impl;

import com.proyecto.demo.module.dto.UserModulesResponse;
import com.proyecto.demo.module.repository.ModuleCustomRepository;
import com.proyecto.demo.module.service.ModuleServiceShard;
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
