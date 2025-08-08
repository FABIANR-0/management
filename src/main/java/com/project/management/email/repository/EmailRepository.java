package com.project.management.email.repository;

import com.project.management.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para la entidad Email.
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {
    /**
     * Recupera una lista de correos electrónicos que aún no han sido enviados.
     * @return Lista de objetos Email que representan correos no enviados.
     */
    @Query("select e from Email e where e.sent = false ")
    List<Email> findEmailsBySent();
}
