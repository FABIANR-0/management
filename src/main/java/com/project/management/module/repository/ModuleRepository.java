package com.project.management.module.repository;

import com.project.management.module.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repositorio para el módulo.
 */
public interface ModuleRepository extends JpaRepository<Module, UUID> {

}
