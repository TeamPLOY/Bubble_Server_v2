package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.exception.InvalidCurrentPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserValidator userValidator;

    private Users user;

    @BeforeEach
    void setUp() {
        user = Users.builder()
                .id(1L)
                .password("encodedPassword") 
                .build();
    }

    @Test
    void validateCurrentPassword_올바른_비밀번호일_경우_예외_발생_안함() {
        // given
        String inputPassword = "correctPassword";
        when(passwordEncoder.matches(inputPassword, user.getPassword())).thenReturn(true);

        // when & then (예외 발생 X)
        userValidator.validateCurrentPassword(user, inputPassword);
    }

    @Test
    void validateCurrentPassword_잘못된_비밀번호일_경우_InvalidCurrentPasswordException_발생() {
        // given
        String wrongPassword = "wrongPassword";
        when(passwordEncoder.matches(wrongPassword, user.getPassword())).thenReturn(false);

        // when & then (예외 발생 O)
        assertThrows(InvalidCurrentPasswordException.class,
                () -> userValidator.validateCurrentPassword(user, wrongPassword));
    }
}
