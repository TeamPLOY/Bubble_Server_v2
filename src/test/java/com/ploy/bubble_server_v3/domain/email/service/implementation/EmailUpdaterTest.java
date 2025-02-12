package com.ploy.bubble_server_v3.domain.email.service.implementation;

import com.ploy.bubble_server_v3.domain.email.exception.EmailSendException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailUpdaterTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailUpdater emailUpdater;

    @BeforeEach
    void 설정() {
        mailSender = mock(JavaMailSender.class);
        emailUpdater = new EmailUpdater(mailSender);
    }

    @Test
    void 이메일_전송_테스트() {
        // Given
        String 받는사람이메일 = "test@example.com";
        String 인증코드 = "123456";

        MimeMessage mimeMessage = mock(MimeMessage.class);

        // When
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        emailUpdater.sendEmail(받는사람이메일, 인증코드);

        // Then
        verify(mailSender).send(mimeMessage);
    }

    @Test
    void 이메일_전송_실패_시_EmailSendException_발생_테스트() {
        // Given
        String 받는사람이메일 = "test@example.com";
        String 인증코드 = "123456";

        MimeMessage mimeMessage = mock(MimeMessage.class);

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        doThrow(new EmailSendException()).when(mailSender).send(mimeMessage);

        // When & Then
        assertThrows(EmailSendException.class, () -> emailUpdater.sendEmail(받는사람이메일, 인증코드));
    }
}