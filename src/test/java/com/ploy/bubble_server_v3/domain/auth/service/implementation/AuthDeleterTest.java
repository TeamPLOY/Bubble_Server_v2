package com.ploy.bubble_server_v3.domain.auth.service.implementation;

import com.ploy.bubble_server_v3.domain.auth.domain.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthDeleterTest {

    private AuthDeleter authDeleter;

    @BeforeEach
    void setUp() {
        authDeleter = new AuthDeleter();
    }

    @Test
    void deleteRefreshToken_호출시_refreshToken_삭제된다() {
        Token token = Token.builder()
                .id(1L)
                .refreshToken("existingRefreshToken")
                .deviceToken("deviceToken123")
                .build();

        authDeleter.deleteRefreshToken(token);

        assertNull(token.getRefreshToken());
    }
}
