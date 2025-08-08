package com.project.management.email.service.impl;

import com.project.management.auth.exception.AuthenticationFailedException;
import com.project.management.email.dto.EmailRequest;
import com.project.management.email.entity.Email;
import com.project.management.email.service.EmailDeliveryService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;


/**
 * Servicio para el envío de correos electrónicos.
 */
@Slf4j
@Service
public class EmailDeliveryServiceImpl implements EmailDeliveryService {

    private final JavaMailSender javaMailSender;
    private final JavaMailSenderImpl javaMailSenderImpl;
    private final EmailServiceImpl emailServiceImpl;

    public EmailDeliveryServiceImpl(JavaMailSender javaMailSender, JavaMailSenderImpl javaMailSenderImpl, EmailServiceImpl emailServiceImpl) {
        this.javaMailSender = javaMailSender;
        this.javaMailSenderImpl = javaMailSenderImpl;
        this.emailServiceImpl = emailServiceImpl;
    }

    /**
     * Envía un correo electrónico utilizando el objeto EmailRequest proporcionado.
     *
     * @param request El objeto EmailRequest que contiene los detalles del correo electrónico.
     * @return Verdadero si el correo electrónico se envió correctamente, de lo contrario falso.
     */
    @Override
    public Boolean send(EmailRequest request) {
        try {
            MimeMessage mimeMessage = generateMimeMessage(request);
            javaMailSender.send(mimeMessage);
            if (request.queue() != null && Boolean.TRUE.equals(request.queue())) {
                saveEmailStatus(request, true, "Mensaje enviado correctamente");
            }
            return true;
        } catch (Exception exception) {
            log.error("Error enviando email a: ({}); Exception ({})", request.destination(), exception.getMessage());
            request.addQueue();
            saveEmailStatus(request, false, exception.getMessage());
            return false;
        }
    }

    @Override
    public Boolean sendTest(EmailRequest request) {
        try {
            MimeMessage mimeMessage = generateMimeMessage(request);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception exception) {
            log.error("Error enviando test email a: ({}); Exception ({})", request.destination(), exception.getMessage());
            return false;
        }
    }

//    /**
//     * Envía un correo electrónico pendiente utilizando el objeto Email proporcionado.
//     *
//     * @param email El objeto Email que contiene los detalles del correo electrónico pendiente.
//     * @return Verdadero si el correo electrónico pendiente se envió correctamente, de lo contrario falso.
//     */
//    @Override
//    public Boolean sendPending(Email email) {
//        try {
//            MimeMessage mimeMessage = generateMimeMessage(EmailRequest.create(email.destination(),email.subject(),email.body()));
//            javaMailSender.send(mimeMessage);
//            return true;
//        } catch (Exception exception) {
//            log.error("Error al tratar de enviar los correos en la cola; Exception ({})", exception.getMessage());
//            return false;
//        }
//    }
//
//    /**
//     * Guarda correos electrónicos pendientes en la base de datos.
//     *
//     * @param emails La lista de correos electrónicos pendientes a guardar.
//     */
//    @Override
//    public void saveEmailPending(List<Email> emails) {
//        emailRepository.saveAll(emails);
//    }


    /**
     * Genera un objeto MimeMessage a partir de la solicitud de correo electrónico proporcionada.
     *
     * @param request La solicitud de correo electrónico que contiene los detalles del mensaje.
     * @return El objeto MimeMessage generado.
     */
    private MimeMessage generateMimeMessage(EmailRequest request) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "utf-8");
            mimeMessageHelper.setTo(request.destination());
            message.setSubject(request.subject());
            mimeMessageHelper.setText(request.body(), true);
            mimeMessageHelper.setFrom(Objects.requireNonNull(javaMailSenderImpl.getUsername()));
            if (request.copy() != null) { // se verifica si el correo va con copia
                mimeMessageHelper.setCc(request.copy());
            }
            if (request.attachedName() != null && request.attached() != null) { // se verifica si el correo va con u  documento adjunto
                mimeMessageHelper.addAttachment(request.attachedName(), Objects.requireNonNull(this.generateFile(request.attached())));
            }
            return message;
        } catch (MessagingException ex) {
            log.error("Error generando el MimeMessage del email ({})", ex.getMessage());
            throw new IllegalArgumentException("Error generando el MimeMessage del email {}");
        }
    }

    /**
     * Genera un archivo a partir de una cadena de Base64.
     *
     * @param base64File la cadena de Base64 del archivo
     * @return el archivo generado
     * @throws AuthenticationFailedException si no se puede generar el archivo adjunto
     */
    private File generateFile(String base64File) {
        try {
            File tempFile = new File("tmp/" + UUID.randomUUID());
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            byte[] data = Base64.decodeBase64(base64File);
            fileOutputStream.write(data);
            fileOutputStream.close();
            return tempFile;
        } catch (IOException ex) {
            log.error("No se pudo generar el documento adjunto ({})", ex.getMessage());

            throw new AuthenticationFailedException("No se pudo generar el documento adjunto");
        }
    }

    /**
     * Guarda el estado del correo electrónico en la base de datos.
     *
     * @param request El objeto EmailRequest que contiene los detalles del correo electrónico.
     * @param status  El estado del correo electrónico (true si se envió correctamente, false si no).
     * @param message El mensaje asociado al estado del correo electrónico.
     */
    private void saveEmailStatus(EmailRequest request, boolean status, String message) {
        if (Boolean.TRUE.equals(request.queue())) {
            Email email = Email.create(request.destination(), Arrays.toString(request.copy()), request.subject(), request.body());
            email.addAttachedDocument(request.attachedName(), request.attached());
            email.addStatus(status, message);
            email.addCount(Boolean.TRUE.equals(status) ? 0 : 1);
            emailServiceImpl.save(email);
        }
    }
}
