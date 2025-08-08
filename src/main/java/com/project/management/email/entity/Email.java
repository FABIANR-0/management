package com.project.management.email.entity;

import com.project.management.common.util.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Accessors(fluent = true)
@Entity
@Table(name = "email", schema = "main")
@EntityListeners(AuditingEntityListener.class)
public class Email extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "email_id", nullable = false)
    private UUID emailId;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "copy", length = 500)
    private String copy;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @Column(name = "sent")
    private Boolean sent;

    @Column(name = "error", columnDefinition = "text")
    private String error;

    @Column(name = "attached", columnDefinition = "text")
    private String attached;

    @Column(name = "attached_name", length = 200)
    private String attachedName;

    @Column(name = "count_sent")
    private Integer countSent;

    /**
     * Constructor de Email.
     */
    public Email() {
    }

    /**
     * Constructor de Email con los datos proporcionados.
     *
     * @param destination el destinatario del correo electrónico
     * @param copy        los destinatarios en copia del correo electrónico
     * @param subject     el asunto del correo electrónico
     * @param body        el cuerpo del correo electrónico
     */
    public Email(String destination, String copy, String subject, String body) {
        this.destination = destination;
        this.copy = copy;
        this.subject = subject;
        this.body = body;
    }

    /**
     * Crea una instancia de Email con los datos proporcionados.
     *
     * @param destination el destinatario del correo electrónico
     * @param copy        los destinatarios en copia del correo electrónico
     * @param subject     el asunto del correo electrónico
     * @param body        el cuerpo del correo electrónico
     * @return una instancia de Email
     */
    public static Email create(String destination, String copy, String subject, String body) {
        return new Email(destination, copy, subject, body);
    }

    /**
     * Agrega el estado de envío y el mensaje de error al correo electrónico.
     *
     * @param sent  el estado de envío (true si se envió con éxito, false si hubo un error)
     * @param error el mensaje de error en caso de que haya ocurrido un error durante el envío
     */
    public void addStatus(Boolean sent, String error) {
        this.sent = sent;
        this.error = error;
    }

    /**
     * Agrega un documento adjunto al correo electrónico.
     *
     * @param attachedName el nombre del archivo adjunto
     * @param attached     el contenido del archivo adjunto
     */
    public void addAttachedDocument(String attachedName, String attached) {
        this.attached = attached;
        this.attachedName = attachedName;
    }

    /**
     * Establece el contador de correos electrónicos enviados.
     *
     * @param countSent El contador de correos electrónicos enviados.
     */
    public void addCount(Integer countSent) {
        this.countSent = countSent;
    }

    /**
     * Incrementa el contador de correos electrónicos enviados y verifica si se han superado los intentos máximos de envío.
     */
    public void incrementSentCount() {
        this.countSent = this.countSent == null ? 1 : this.countSent + 1;
        if (this.countSent >= 3) {
            this.sent = true;
            this.error = "Cantidad de intentos de envío superado";
        }
    }
}
