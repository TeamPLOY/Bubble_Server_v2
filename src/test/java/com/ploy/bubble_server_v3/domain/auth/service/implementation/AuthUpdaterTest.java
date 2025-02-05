package com.ploy.bubble_server_v3.domain.auth.service.implementation;

import com.ploy.bubble_server_v3.common.jwt.Jwt;
import com.ploy.bubble_server_v3.common.jwt.dto.TokenResponse;
import com.ploy.bubble_server_v3.domain.auth.domain.Token;
import com.ploy.bubble_server_v3.domain.auth.domain.repository.TokenRepository;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUpdaterTest {

    @Mock
    private Jwt jwt;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthUpdater authUpdater;

    private Users user;
    private Token existingToken;
    private TokenResponse tokenResponse;
    private String deviceToken;

    @BeforeEach
    void setUp() {
        user = Users.builder()
                .id(1L)
                .email("test@example.com")
                .name("Test User")
                .build();

        deviceToken = "deviceToken123";

        existingToken = Token.builder()
                .user(user)
                .deviceToken(deviceToken)
                .refreshToken("oldRefreshToken")
                .build();

        tokenResponse = new TokenResponse("newAccessToken", "newRefreshToken");
    }

    @Test
    void publishToken_기존토큰이_있으면_RefreshToken_업데이트() {
        when(jwt.generateAllToken(any())).thenReturn(tokenResponse);

        TokenResponse result = authUpdater.publishToken(user, deviceToken, existingToken);

        assertEquals(tokenResponse.accessToken(), result.accessToken());
        assertEquals(tokenResponse.refreshToken(), result.refreshToken());
        assertEquals(tokenResponse.refreshToken(), existingToken.getRefreshToken());

        verify(tokenRepository, times(1)).save(existingToken);
    }

    @Test
    void publishToken_기존토큰이_없으면_새로운_토큰_생성() {
        when(jwt.generateAllToken(any())).thenReturn(tokenResponse);

        TokenResponse result = authUpdater.publishToken(user, deviceToken, null);

        assertEquals(tokenResponse.accessToken(), result.accessToken());
        assertEquals(tokenResponse.refreshToken(), result.refreshToken());

        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void refreshToken_정상적으로_RefreshToken_업데이트() {
        when(jwt.generateAllToken(any())).thenReturn(tokenResponse);

        TokenResponse result = authUpdater.refreshToken(existingToken);

        assertEquals(tokenResponse.accessToken(), result.accessToken());
        assertEquals(tokenResponse.refreshToken(), result.refreshToken());
        assertEquals(tokenResponse.refreshToken(), existingToken.getRefreshToken());

        verify(tokenRepository, times(1)).save(existingToken);
    }
}
