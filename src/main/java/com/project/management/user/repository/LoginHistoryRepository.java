package com.project.management.user.repository;

import com.project.management.user.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositorio para la entidad LoginHistory.
 * Proporciona métodos de acceso a datos para la entidad LoginHistory.
 */
@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, UUID> {

}
