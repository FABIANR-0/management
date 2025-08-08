package com.project.management.role.repository;

import com.project.management.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio del modúlo
 */
public interface RoleRepository extends JpaRepository<Role, UUID>, RoleRepositoryCustom {

    /**
     *  Obtener lista de roles por sus identíficadores
     * @param idsRole identíficadores de los roles que se vana  consultar
     * @return lista de roles obtenidos.
     */
    List<Role> getRolesByRoleIdIn(List<UUID> idsRole);

    Boolean existsByNameIgnoreCase(String name);

    List<Role> getRolesByNameIgnoreCaseIn(List<String> roleName);

    List<Role> getAllByNameNot(String name);
}
