package com.ploy.bubble_server_v3.domain.email.service;

import com.ploy.bubble_server_v3.domain.email.exception.EmailNotFoundException;
import com.ploy.bubble_server_v3.domain.email.presentation.dto.request.EmailCertificationRequest;
import com.ploy.bubble_server_v3.domain.email.presentation.dto.request.EmailSendRequest;
import com.ploy.bubble_server_v3.domain.email.service.implementation.EmailUpdater;
import com.ploy.bubble_server_v3.domain.email.service.implementation.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandEmailService {
    private final StringRedisTemplate redisTemplate;
    private final EmailValidator emailValidator;
    private final EmailUpdater emailUpdater;

    public void sendEmail(EmailSendRequest request) {
        emailValidator.validate(request.email());
        String code = String.valueOf((int) (Math.random() * 900000) + 100000);
        redisTemplate.opsForValue().set(request.email(), code, 5, TimeUnit.MINUTES);
        emailUpdater.sendEmail(request.email(), code);
    }

    public boolean certificationEmail(EmailCertificationRequest request) {
        emailValidator.validateAndDeleteEmailCode(request.email(), request.code());
        return true;
    }
}