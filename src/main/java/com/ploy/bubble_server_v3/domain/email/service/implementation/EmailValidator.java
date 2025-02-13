package com.ploy.bubble_server_v3.domain.email.service.implementation;

import com.ploy.bubble_server_v3.domain.email.exception.InvalidEmailFormatException;
import com.ploy.bubble_server_v3.domain.email.exception.EmailNotFoundException;
import com.ploy.bubble_server_v3.domain.email.exception.CodeMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailValidator {
    private final StringRedisTemplate redisTemplate;

    public void validate(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new InvalidEmailFormatException();
        }
    }

    public void validateAndDeleteEmailCode(String email, String code) {
        String storedCode = redisTemplate.opsForValue().get(email);
        if (storedCode == null) {
            throw new EmailNotFoundException();
        }
        if (!storedCode.equals(code)) {
            throw new CodeMismatchException();
        }

        redisTemplate.delete(email);
    }
}