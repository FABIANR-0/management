package com.project.management.email.service.impl;

import com.project.management.user.dto.UserAuthorizedDto;
import com.project.management.user.entity.User;
import com.project.management.email.dto.EmailRequest;
import com.project.management.email.service.EmailDeliveryService;
import com.project.management.email.service.EmailTemplateService;
import com.project.management.email.service.EmailUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Servicio de gestión de correo electrónico para usuarios.
 */
@Service
@Slf4j
public class EmailUserServiceImpl implements EmailUserService {

    private final EmailDeliveryService emailDeliveryService;

    private final EmailTemplateService emailTemplateService;

    public EmailUserServiceImpl(EmailDeliveryService emailDeliveryService, EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
        this.emailDeliveryService = emailDeliveryService;
    }

    /**
     * Envía un correo electrónico del código de verificación del usuario.
     *
     * @param user El objeto User que contiene la data de un usuario al que se enviará el correo electrónico de verificación.
     * @return Verdadero si el correo electrónico de verificación se envió correctamente, de lo contrario falso.
     */
    @Override
    public Boolean sendCodeVerification(User user) {
        String body = emailTemplateService.getEmailTemplateByName("two_factor_authentication")
                .replace("person_name", user.getPerson().getName())
                .replace("verification_code_1", String.valueOf(user.getCodeVerification().charAt(0)))
                .replace("verification_code_2", String.valueOf(user.getCodeVerification().charAt(1)))
                .replace("verification_code_3", String.valueOf(user.getCodeVerification().charAt(2)))
                .replace("verification_code_4", String.valueOf(user.getCodeVerification().charAt(3)))
                .replace("verification_code_5", String.valueOf(user.getCodeVerification().charAt(4)))
                .replace("verification_code_6", String.valueOf(user.getCodeVerification().charAt(5)));

        return emailSend(user.getPerson().getEmail(), "Tu código de verificación", body);
    }

    /**
     * Envía un correo electrónico de recuperación de contraseña al usuario.
     *
     * @param user El usuario al que se enviará el correo electrónico de recuperación de contraseña.
     * @param urlToken El token generado URL para la recuperación de contraseña.
     */
    @Override
    public void recoveryPasswordEmailSent(User user, String urlToken) {
        String body = emailTemplateService.getEmailTemplateByName("recovery_password");
        body = body.replace("person_name", user.getPerson().getName());
        body = body.replace("link_recovery_password", urlToken);

        emailSend(user.getPerson().getEmail(), "Recuperación de contraseña", body);
    }

    /**
     * Envía un correo electrónico de cambio exitoso de contraseña al usuario.
     *
     * @param user El usuario al que se enviará el correo electrónico de cambio exitoso de contraseña.
     */
    @Override
    public void changePassword(User user) {
        String body = emailTemplateService.getEmailTemplateByName("change_password");
        body = body.replace("person_name", user.getPerson().getName());

        emailSend(user.getPerson().getEmail(), "Cambio de contraseña exitoso", body);
    }

    /**
     * Reseteo de la contraseña de un usuario por el admin y notifica al usuario por correo electrónico.
     *
     * @param user El usuario al cual la contraseña se le va a resetear.
     * @param password La nueva contraseña para el usuario.
     */
    @Override
    public void resetUserPassword(User user, String password) {
        String body = emailTemplateService.getEmailTemplateByName("reset_user_password_admin");
        body = body.replace("person_name", user.getPerson().getName());
        body = body.replace("user_name", user.getName());
        body = body.replace("user_password", password);

        emailSend(user.getPerson().getEmail(), "Cambio de contraseña exitoso por el Administrador", body);

    }

    /**
     * Envía las credenciales de un nuevo usuario autorizado por correo electrónico.
     *
     * @param request Los detalles del usuario autorizado.
     * @param userAuth El usuario que autorizó la creación de la cuenta.
     * @param randomPassword La contraseña generada para la nueva cuenta de usuario.
     * @param applicationName El nombre de la aplicación a la que se asociará la cuenta de usuario.
     */
    @Override
    public void sendUserCredentials(UserAuthorizedDto request, User userAuth, String randomPassword, String applicationName) {
        String body = emailTemplateService.getEmailTemplateByName("new_user_authorized");
        body = body.replace("person_name", request.getPerson().getName());
        body = body.replace("application_name", applicationName);
        body = body.replace("commerce_name", "wposs");
        body = body.replace("user_name", request.getName().toLowerCase(Locale.ROOT));
        body = body.replace("user_password", randomPassword);

        emailSend(request.getPerson().getEmail(), "Detalles de tu nueva cuenta de usuario", body);
    }

    @Override
    public Boolean testSendEmail(String mail, String smtp, String port){
        try {
            // Obtiene el cuerpo del correo electrónico y reemplaza variables específicas
            String body = emailTemplateService.getEmailTemplateByName("test_email");
            body = body.replace("{email_new}", mail);
            body = body.replace("{server_smtp}", smtp);
            body = body.replace("{port}", port);

            return emailDeliveryService.sendTest(EmailRequest.create(mail, "Ajustes del envío de correos.", body));
        } catch (Exception e) {
            log.error("Error al enviar el correo electrónico para el test: {}", e.getMessage());
            return false;
        }
    }
    /**
     * Envía un correo electrónico con los detalles proporcionados.
     *
     * @param email La dirección de correo electrónico del destinatario.
     * @param subject El asunto del correo electrónico.
     * @param body El cuerpo del correo electrónico.
     */
    private Boolean emailSend(String email, String subject, String body) {
        EmailRequest emailRequest = EmailRequest.create(
                email,
                subject,
                body
        );
        return emailDeliveryService.send(emailRequest);
    }

}
