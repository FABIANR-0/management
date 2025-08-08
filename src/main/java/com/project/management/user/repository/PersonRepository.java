package com.project.management.user.repository;

import com.project.management.user.entity.Person;
import com.project.management.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad Person.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    /**
     * Comprueba si existe una persona con el correo electrónico especificado.
     *
     * @param email Correo electrónico a comprobar.
     * @return `true` si existe una persona con el correo electrónico, `false` en caso contrario.
     */
    Boolean existsByEmail(String email);

    Person findByUser(User user);
}
