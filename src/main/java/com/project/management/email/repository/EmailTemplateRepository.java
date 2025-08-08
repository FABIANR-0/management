package com.project.management.email.repository;

import com.project.management.email.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad EmailTemplate.
 */
@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, String> {

    /**
     *  Recupera una plantilla de correo electrónico por su nombre, si existe.
     * @param templateName Nombre de la plantilla de correo electrónico a buscar.
     * @return Un Optional que contiene la plantilla de correo electrónico si se encuentra, o vacío si no existe.
     */
    Optional<EmailTemplate> getEmailTemplateByName(String templateName);
}
