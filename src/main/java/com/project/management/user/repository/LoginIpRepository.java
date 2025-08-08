package com.project.management.user.repository;

import com.project.management.user.entity.LoginIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad LoginIp.
 */
@Repository
public interface LoginIpRepository extends JpaRepository<LoginIp, UUID> {

    /**
     * Verifica si existe un LoginIp con el valor y el ID de usuario especificados.
     *
     * @param value   El valor del LoginIp.
     * @param userId  El ID del usuario.
     * @return true si existe un LoginIp con el valor y el ID de usuario especificados, false de lo contrario.
     */
    Boolean existsLoginIpByValueAndUser_UserId(String value, UUID userId);
}
