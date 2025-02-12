package com.ploy.bubble_server_v3.domain.email.service.implementation;

import com.ploy.bubble_server_v3.domain.email.exception.InvalidEmailFormatException;
import com.ploy.bubble_server_v3.domain.email.exception.EmailNotFoundException;
import com.ploy.bubble_server_v3.domain.email.exception.CodeMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;

class EmailValidatorTest {

    @InjectMocks
    private EmailValidator emailValidator;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validate_유효한이메일_형식_성공() {
        // Given
        String validEmail = "test@example.com";

        // When & Then
        emailValidator.validate(validEmail); // 예외가 발생하지 않으면 테스트 통과
    }

    @Test
    void validate_잘못된이메일_형식_실패() {
        // Given
        String invalidEmail = "test@example";

        // When & Then
        // InvalidEmailFormatException이 발생해야 함
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEmailFormatException.class, () -> {
            emailValidator.validate(invalidEmail);
        });
    }

    @Test
    void validateAndDeleteEmailCode_이메일코드_일치_성공() {
        // Given
        String email = "test@example.com";
        String code = "123456";
        String storedCode = "123456";

        // Redis에서 해당 이메일에 대한 코드가 저장되어 있다고 가정
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(email)).thenReturn(storedCode);

        // When & Then
        emailValidator.validateAndDeleteEmailCode(email, code); // 예외가 발생하지 않으면 테스트 통과

        // Redis에서 이메일 삭제가 호출되었는지 확인
        verify(redisTemplate).delete(email);
    }

    @Test
    void validateAndDeleteEmailCode_이메일_없음_실패() {
        // Given
        String email = "test@example.com";
        String code = "123456";

        // Redis에서 해당 이메일에 대한 코드가 존재하지 않음
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(email)).thenReturn(null);

        // When & Then
        // EmailNotFoundException이 발생해야 함
        org.junit.jupiter.api.Assertions.assertThrows(EmailNotFoundException.class, () -> {
            emailValidator.validateAndDeleteEmailCode(email, code);
        });
    }

    @Test
    void validateAndDeleteEmailCode_코드불일치_실패() {
        // Given
        String email = "test@example.com";
        String code = "123456";
        String storedCode = "654321"; // 저장된 코드와 불일치

        // Redis에서 해당 이메일에 대한 코드가 존재
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(email)).thenReturn(storedCode);

        // When & Then
        // CodeMismatchException이 발생해야 함
        org.junit.jupiter.api.Assertions.assertThrows(CodeMismatchException.class, () -> {
            emailValidator.validateAndDeleteEmailCode(email, code);
        });
    }
}