package com.ploy.bubble_server_v3.domain.auth.service.implementation;

import com.ploy.bubble_server_v3.domain.auth.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthValidatorTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthValidator authValidator;

    private final String rawPassword = "password123";
    private final String encodedPassword = "$2a$10$abcdefg";

    @Test
    void validatePassword_비밀번호_일치_예외없음() {
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        assertDoesNotThrow(() -> authValidator.validatePassword(rawPassword, encodedPassword));
    }

    @Test
    void validatePassword_비밀번호_불일치_InvalidPasswordException_발생() {
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> authValidator.validatePassword(rawPassword, encodedPassword));
    }
}
