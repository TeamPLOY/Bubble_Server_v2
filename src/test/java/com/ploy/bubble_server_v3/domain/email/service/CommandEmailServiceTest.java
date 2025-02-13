package com.ploy.bubble_server_v3.domain.email.service;

import com.ploy.bubble_server_v3.domain.email.presentation.dto.request.EmailCertificationRequest;
import com.ploy.bubble_server_v3.domain.email.presentation.dto.request.EmailSendRequest;
import com.ploy.bubble_server_v3.domain.email.service.implementation.EmailUpdater;
import com.ploy.bubble_server_v3.domain.email.service.implementation.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class CommandEmailServiceTest {

    @InjectMocks
    private CommandEmailService commandEmailService;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private EmailUpdater emailUpdater;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void sendEmail_이메일전송_성공() {
        String email = "test@example.com";
        EmailSendRequest request = new EmailSendRequest(email);

        commandEmailService.sendEmail(request);

        verify(emailValidator, times(1)).validate(eq(email));
        verify(valueOperations, times(1)).set(eq(email), anyString(), eq(5L), eq(TimeUnit.MINUTES));
        verify(emailUpdater, times(1)).sendEmail(eq(email), anyString());
    }

    @Test
    void certificationEmail_이메일코드_일치_성공() {
        String email = "test@example.com";
        String code = "123456";
        EmailCertificationRequest request = new EmailCertificationRequest(code,email);

        when(valueOperations.get(email)).thenReturn(code);

        commandEmailService.certificationEmail(request);

        verify(emailValidator, times(1)).validateAndDeleteEmailCode(eq(email), eq(code)); // Fixed argument order
    }
}