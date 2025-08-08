package com.project.management.permission.repository;

import com.project.management.permission.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio de datos para la entidad Permissions. Proporciona métodos para realizar operaciones CRUD en la tabla de permissions.
 * Extiende JpaRepository para heredar métodos de repositorio genéricos.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    /**
     * Obtener lista de permisos por sus identificadores
     * @param permissions Lista de identificadores
     * @return Lista de objetos de permisos
     */
    List<Permission> getPermissionByPermissionIdIn(List<UUID> permissions);

    /**
     * Obtener lista de permisos de un rol en específico
     * @param id identificador del rol
     * @return Lista de permisos asociados al rol
     */
    List<Permission> findAllByRoles_RoleId(UUID id);

    Permission getPermissionByModuleId(UUID moduleId);
}
