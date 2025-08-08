package com.project.management.email.service.impl;

import com.project.management.email.entity.EmailTemplate;
import com.project.management.email.repository.EmailTemplateRepository;
import com.project.management.email.service.EmailTemplateService;
import com.project.management.common.exception_handler.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Year;

/**
 * Servicio para el manejo de plantillas de correos electrónicos.
 */
@Service
@Slf4j
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;

    @Value("${settings.application.name}")
    private String applicationName;

    @Value("${settings.url.front}")
    private String urlFrontEnd;

    @Value("${settings.application.web-site}")
    private String webSite;

    @Value("${settings.social-networks.facebook}")
    private String facebook;

    @Value("${settings.social-networks.instagram}")
    private String instagram;

    @Value("${settings.social-networks.linkedin}")
    private String linkedin;

    public EmailTemplateServiceImpl(EmailTemplateRepository emailTemplateRepository) {
        this.emailTemplateRepository = emailTemplateRepository;
    }

    /**
     * Este metodo consulta la plantilla del correo a la base de datos, luego remplaza las variables globales de la plantillas y devielbe el body del correo
     *
     * @return el cuerpo del correo electrónico con las variables remplazadas
     */
    private String getBodyWithBasicContent(String body) {

        body = body.replace("domain", urlFrontEnd);
        body = body.replace("application_name", applicationName);
        body = body.replace("web_site", webSite);
        body = body.replace("social_facebook", facebook);
        body = body.replace("social_instagram", instagram);
        body = body.replace("social_linkedin", linkedin);
        body = body.replace("current_year", String.valueOf(Year.now().getValue()));

        return body;
    }

    /**
     * Obtiene una plantilla de correo electrónico por nombre.
     *
     * @param name El nombre de la plantilla de correo electrónico.
     * @return El contenido de la plantilla de correo electrónico.
     * @throws ResourceNotFoundException Si la plantilla de correo electrónico no se encuentra.
     */
    @Override
    public String getEmailTemplateByName(String name) {
        try{
            EmailTemplate emailTemplate = emailTemplateRepository.getEmailTemplateByName(name).get();
            emailTemplate.replaceBody(getBodyWithBasicContent(emailTemplate.body()));
            return emailTemplate.body();
        }catch (Exception e){
           log.error("Tenemos un error con el servicio de correos (plantilla no encontrada), inténtelo más tarde; Error: [{}]", e.getMessage());
           return null;
        }
    }
}
