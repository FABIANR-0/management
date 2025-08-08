package com.project.management.user.repository;

import com.project.management.role.entity.Role;
import com.project.management.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {

    /**
     * Obtiene un usuario por su nombre.
     *
     * @param name el nombre del usuario
     * @return una instancia de User o null si no se encuentra
     */
    User getUserByName(String name);


    /**
     * Comprueba si existe una persona con el nombre especificado.
     *
     * @param name Nombre a comprobar.
     * @return `true` si existe una persona con el nombre, `false` en caso contrario.
     */
    Boolean existsByName(String name);

    List<User> findAllByRolesIs(List<Role> roles);

    Optional<User> findByNameAndPersonEmail(String userName, String email);

    Optional<User> findByUserIdAndName(UUID userId, String name);

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r = :role")
    long countUsersByRole(@Param("role") Role role);
}
