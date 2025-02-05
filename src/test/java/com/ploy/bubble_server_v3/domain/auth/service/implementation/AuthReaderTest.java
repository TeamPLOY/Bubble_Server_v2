package com.ploy.bubble_server_v3.domain.auth.service.implementation;

import com.ploy.bubble_server_v3.domain.auth.domain.Token;
import com.ploy.bubble_server_v3.domain.auth.domain.repository.TokenRepository;
import com.ploy.bubble_server_v3.domain.auth.exception.EmailAlreadyExistsException;
import com.ploy.bubble_server_v3.domain.auth.exception.TokenNotFoundException;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthReaderTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthReader authReader;

    private Users user;
    private Token token;

    @BeforeEach
    void setUp() {
        user = Users.builder()
                .id(1L)
                .email("test@example.com")
                .name("Test User")
                .build();

        token = Token.builder()
                .user(user)
                .refreshToken("refreshToken123")
                .deviceToken("deviceToken123")
                .build();
    }

    @Test
    void isEmailExist_존재하는_이메일이면_예외발생() {
        when(usersRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> authReader.isEmailExist(user.getEmail()));
    }

    @Test
    void isEmailExist_존재하지_않는_이메일이면_false_반환() {
        when(usersRepository.existsByEmail(user.getEmail())).thenReturn(false);

        assertFalse(authReader.isEmailExist(user.getEmail()));
    }

    @Test
    void findTokenByUserAndDeviceToken_토큰이_존재하면_반환() {
        when(tokenRepository.findByUserIdAndDeviceToken(user.getId(), token.getDeviceToken()))
                .thenReturn(Optional.of(token));

        Token result = authReader.findTokenByUserAndDeviceToken(user, token.getDeviceToken());

        assertEquals(token, result);
    }

    @Test
    void findTokenByUserAndDeviceToken_토큰이_없으면_새로운_객체_반환() {
        when(tokenRepository.findByUserIdAndDeviceToken(user.getId(), token.getDeviceToken()))
                .thenReturn(Optional.empty());

        Token result = authReader.findTokenByUserAndDeviceToken(user, token.getDeviceToken());

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(token.getDeviceToken(), result.getDeviceToken());
        assertNull(result.getRefreshToken());  // 새로 생성된 객체는 refreshToken이 null이어야 함.
    }

    @Test
    void findByRefreshToken_토큰이_존재하면_반환() {
        when(tokenRepository.findByRefreshToken(token.getRefreshToken()))
                .thenReturn(Optional.of(token));

        Token result = authReader.findByRefreshToken(token.getRefreshToken());

        assertEquals(token, result);
    }

    @Test
    void findByRefreshToken_토큰이_없으면_예외발생() {
        when(tokenRepository.findByRefreshToken("invalidToken")).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> authReader.findByRefreshToken("invalidToken"));
    }
}
