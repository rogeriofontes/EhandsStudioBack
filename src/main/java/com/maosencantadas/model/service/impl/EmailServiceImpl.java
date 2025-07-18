package com.maosencantadas.model.service.impl;

import com.maosencantadas.commons.Constants;
import com.maosencantadas.model.service.EmailService;
import com.maosencantadas.utils.RestUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${app.domain.url}")
    private String domainUrl;

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void publish(String name, String to, String subject, boolean isArtist, String validatedToken) throws Exception {
        log.info("Sending email with subject: {}", subject);

        String activationLink = getContextUrlToValidateToken(validatedToken);
        log.info("Activation link generated: {}", activationLink);

        if (isArtist) {
            String bodyArtist = toBodyArtist(name, activationLink);
            log.info("Sending email with subject, for associate: {}", subject);
            sendMail(to, subject, bodyArtist);
        } else {
            String body = toBody(name);
            log.info("Sending email with subject, for user: {}", subject);
            sendMail(to, subject, body);
        }
    }

    @Override
    public void publishPasswordRecovery(String name, String to, String subject, String token) throws Exception {
        log.info("Sending email with subject, for password recovery: {}", subject);

        String resetLink = getContextUrlToResetPassword(token);
        log.info("Password reset link generated: {}", resetLink);

        String body = toBodyPasswordRecovery(name, resetLink);
        sendMail(to, subject, body);
    }

    private void sendMail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(Constants.FROM_EMAIL);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true indicates HTML

        mailSender.send(message);
    }

    public String toBody(String name) {
        return "<h1>Bem-vindo, " + name + "!</h1>"
                + "<p>Obrigado por se registrar no nosso site.</p>";
    }

    public String toBodyArtist(String name, String activationLink) {
        return "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<style>" +
                "body { background: #f4f4f7; margin: 0; padding: 0; font-family: Arial, sans-serif; }" +
                ".container { background: #fff; max-width: 480px; margin: 40px auto; padding: 32px 24px; border-radius: 14px; box-shadow: 0 4px 24px rgba(0,0,0,0.07); }" +
                "h1 { color: #2563eb; font-size: 26px; margin-bottom: 8px; }" +
                "p { color: #444; font-size: 16px; margin-top: 0; }" +
                ".btn { display: inline-block; margin: 24px 0 18px 0; padding: 12px 28px; background: #2563eb; color: #fff !important; font-size: 17px; border-radius: 8px; text-decoration: none; font-weight: bold; box-shadow: 0 2px 6px rgba(37,99,235,0.09); transition: background 0.18s; }" +
                ".btn:hover { background: #1a4ec6; }" +
                ".small { font-size: 12px; color: #888; margin-top: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<h1>Olá, " + name + "!</h1>" +
                "<p>Seja bem-vindo(a) à nossa plataforma!<br>" +
                "Para ativar sua conta, clique no botão abaixo:</p>" +
                "<a class='btn' href='" + activationLink + "' target='_blank'>Ativar Conta</a>" +
                "<p class='small'>Se não solicitou este cadastro, apenas ignore este e-mail.</p>" +
                "<p style='margin-top: 28px; color: #888;'>Obrigado por se registrar em nosso projeto.<br /><br />Equipe Mãos Encantadas</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    public String toBodyPasswordRecovery(String name, String resetLink) {
        return "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<style>" +
                "body { background: #f4f4f7; font-family: Arial, sans-serif; margin: 0; padding: 0; }" +
                ".container { background: #fff; max-width: 480px; margin: 40px auto; padding: 32px 24px; border-radius: 14px; box-shadow: 0 4px 24px rgba(0,0,0,0.07); }" +
                "h1 { color: #2563eb; font-size: 24px; margin-bottom: 16px; }" +
                "p { color: #444; font-size: 16px; margin-top: 0; }" +
                ".btn { display: inline-block; margin: 22px 0 14px 0; padding: 12px 28px; background: #2563eb; color: #fff !important; font-size: 17px; border-radius: 8px; text-decoration: none; font-weight: bold; }" +
                ".btn:hover { background: #1a4ec6; }" +
                ".small { font-size: 12px; color: #888; margin-top: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<h1>Olá, " + name + "!</h1>" +
                "<p>Recebemos uma solicitação para redefinir a sua senha.<br>" +
                "Clique no botão abaixo para criar uma nova senha:</p>" +
                "<a class='btn' href='" + resetLink + "' target='_blank'>Redefinir Senha</a>" +
                "<p class='small'>Se você não solicitou esta alteração, apenas ignore este e-mail.</p>" +
                "<p style='margin-top: 28px; color: #888;'>Atenciosamente,<br>Equipe Mãos Encantadas</p>" +
                "</div>" +
                "</body>" +
                "</html>";

    }

    private static String getContextUrlToValidateToken(String validatedToken) {
        String appUrl = RestUtils.getAppUrl();
        return appUrl + "/auth/activation-token?token=" + validatedToken;
    }

    private String getContextUrlToResetPassword(String resetToken) {
        return domainUrl + "/reset-password?token=" + resetToken;
    }
}
