package com.project.management.email.dto;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Clase que representa una solicitud de envío de correo electrónico.
 */
@Getter
@Accessors(fluent = true)
public class EmailRequest {

    private final String destination;
    private String[] copy;
    private final String subject;
    private final String body;
    private String attached;
    private String attachedName;
    private Boolean queue;

    /**
     * Constructor de EmailRequest.
     *
     * @param destination el destinatario del correo electrónico
     * @param subject     el asunto del correo electrónico
     * @param body        el cuerpo del correo electrónico
     */
    public EmailRequest(String destination, String subject, String body) {
        this.destination = destination;
        this.subject = subject;
        this.body = body;
        this.queue = false;
    }

    /**
     * Crea una instancia de EmailRequest con los datos proporcionados.
     *
     * @param destination el destinatario del correo electrónico
     * @param subject     el asunto del correo electrónico
     * @param body        el cuerpo del correo electrónico
     * @return una instancia de EmailRequest
     */
    public static EmailRequest create(String destination, String subject, String body) {
        return new EmailRequest(destination, subject, body);
    }

    /**
     * Agrega una lista de destinatarios en copia del correo electrónico.
     *
     * @param copy la lista de destinatarios en copia
     */
    public void addCopy(String[] copy) {
        this.copy = copy;
    }

    /**
     * Agrega un documento adjunto al correo electrónico.
     *
     * @param attachedName el nombre del archivo adjunto
     * @param attached     el contenido del archivo adjunto
     */
    public void addAttachedDocument(String attachedName, String attached) {
        this.attachedName = attachedName;
        this.attached = attached;
    }

    /**
     * Agrega el correo electrónico a la cola de envío.
     */
    public void addQueue() {
        this.queue = true;
    }

}
