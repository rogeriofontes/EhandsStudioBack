package com.maosencantadas.model.service.impl;

import com.maosencantadas.commons.Constants;
import com.maosencantadas.model.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ff4j.FF4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void publish(String name, String to, String subject, boolean isArtist, String validatedToken) throws Exception {
        log.info("Sending email with subject: {}", subject);

        String activationLink = getContextUrl(validatedToken);
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

   /* @Override
    public void publishPasswordRecovery(String name, String to, String subject, String newPassword, String validatedToken) throws Exception {
        log.info("Sending email with subject, for password recovery: {}", subject);

        String body = toBodyPasswordRecovery(name, newPassword);
        sendMail(to, subject, body);
    } */

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
                "<style>" +
                "body { font-family: Arial, sans-serif; margin: 20px; }" +
                "h1 { color: #333; }" +
                "p { font-size: 14px; color: #555; }" +
                "ol { padding-left: 20px; }" +
                "li { margin-bottom: 10px; }" +
                "a { color: #1a73e8; text-decoration: none; }" +
                "a:hover { text-decoration: underline; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>Olá, " + name + "!</h1>" +
                "<p>Seja bem-vinda à nossa plataforma. Para ativar sua conta, clique no link abaixo:</p>" +
                "<ol>" +
                "<li><b>Valide seu cadastro:</b> " + activationLink + " </li>" +
                "</ol>" +
                "<p>Obrigado por se registrar em nosso projeto.</p>" +
                "</body>" +
                "</html>";
    }

    public String toBodyPasswordRecovery(String name, String password) {
        return "<h1>Bem-vindo, " + name + "!</h1>"
                + "<p>Sua senha é: " + password + "</p>"
                + "<p>Obrigado por se registrar no nosso site.</p>";
    }

    private static @NotNull String getContextUrl(String validatedToken) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new IllegalStateException("Requisição fora de contexto HTTP");
        }

        HttpServletRequest request = attrs.getRequest();
        String appUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());

        return appUrl + "/activated?token=" + validatedToken;
    }
}
