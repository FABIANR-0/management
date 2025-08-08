package com.project.management.email.service.impl;

import com.project.management.email.entity.Email;
import com.project.management.email.repository.EmailRepository;
import com.project.management.email.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para el manejo de correos electrónicos.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    public EmailServiceImpl(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    /**
     * Guarda un correo electrónico en el repositorio.
     *
     * @param email el correo electrónico a guardar
     */
    @Override
    public void save(Email email) {
        emailRepository.save(email);
    }

    /**
     * Obtiene la lista de correos electrónicos pendientes.
     *
     * @return La lista de correos electrónicos pendientes.
     */
    @Override
    public List<Email> getPending() {
        return emailRepository.findEmailsBySent();
    }
}
