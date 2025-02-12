package com.ploy.bubble_server_v3.domain.email.service.implementation;

import com.ploy.bubble_server_v3.domain.email.exception.EmailSendException;
import com.ploy.bubble_server_v3.domain.email.exception.TemplateLoadException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class EmailUpdater {
    private final JavaMailSender mailSender;

    public void sendEmail(String recipientEmail, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setSubject("이메일 인증 코드");
            String htmlContent = getHtmlContent(code);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendException();
        }
    }

    private String getHtmlContent(String code) {
        try {
            ClassPathResource resource = new ClassPathResource("templates/email-template.html");
            String htmlTemplate = new String(Files.readAllBytes(Paths.get(resource.getURI())));
            return htmlTemplate.replace("{{code}}", code);
        } catch (IOException e) {
            throw new TemplateLoadException();
        }
    }
}