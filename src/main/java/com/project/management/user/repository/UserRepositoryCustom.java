package com.project.management.user.repository;

import com.project.management.auth.dto.AuthModule;
import com.project.management.auth.dto.Permission;
import com.project.management.common.criteria.Criteria;
import com.project.management.user.dto.UserResponse;
import com.project.management.user.dto.UserTechnical;
import com.project.management.user.dto.UsersAuthorizedResponse;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryCustom {

    /**
     * Obtiene el usuario que cumplen con el criterio de búsqueda.
     *
     * @param userId  El identificador del usuario que se desea buscar
     * @return un objeto del resultado del usuario
     */
    UserResponse getUserId(UUID userId);

    /**
     * Obtiene la lista de módulos de autorización de un usuario.
     *
     * @param userId Identificador único del usuario.
     * @return Lista de objetos AuthModule que representan los módulos de autorización del usuario.
     */
    List<AuthModule> findModulesByUser(UUID userId);

    List<Permission> findPermissionsByUserName(String userName);

    List<UsersAuthorizedResponse> getUsersAuthorizedByAdmin(Criteria criteria, String search);

    Long countUserAuthorized();

    List<UserTechnical> getAllUsersByRole();
}
