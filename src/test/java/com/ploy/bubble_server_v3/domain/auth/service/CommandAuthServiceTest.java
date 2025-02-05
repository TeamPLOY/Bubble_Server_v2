package com.ploy.bubble_server_v3.domain.auth.service;

import com.ploy.bubble_server_v3.common.jwt.dto.TokenResponse;
import com.ploy.bubble_server_v3.domain.auth.domain.Token;
import com.ploy.bubble_server_v3.domain.auth.exception.EmailAlreadyExistsException;
import com.ploy.bubble_server_v3.domain.auth.exception.InvalidPasswordException;
import com.ploy.bubble_server_v3.domain.auth.exception.TokenNotFoundException;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.*;
import com.ploy.bubble_server_v3.domain.auth.service.implementation.*;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserCreator;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserDeleter;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandAuthServiceTest {

    @Mock private AuthReader authReader;
    @Mock private UserCreator userCreator;
    @Mock private AuthUpdater authUpdater;
    @Mock private AuthValidator authValidator;
    @Mock private AuthDeleter authDeleter;
    @Mock private UserReader userReader;
    @Mock private UserDeleter userDeleter;

    @InjectMocks private CommandAuthService commandAuthService;

    private final String email = "test@example.com";
    private final String password = "password123";
    private final String encodedPassword = "$2a$10$encodedpassword";
    private final String deviceToken = "device123";
    private final String refreshToken = "refreshToken123";
    private final Long userId = 1L;

    private Users user;
    private Token token;
    private TokenResponse tokenResponse;

    @BeforeEach
    void setUp() {
        user = Users.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        token = Token.builder()
                .user(user)
                .deviceToken(deviceToken)
                .refreshToken(refreshToken)
                .build();

        tokenResponse = new TokenResponse("accessToken", "newRefreshToken");
    }

    @Test
    void signUp_이메일존재시_EmailAlreadyExistsException_발생() {
        SignUpRequest request = new SignUpRequest("test@example.com", "password123", "TestUser", 2116, "B314");

        doThrow(new EmailAlreadyExistsException()).when(authReader).isEmailExist(email);

        assertThrows(EmailAlreadyExistsException.class, () -> commandAuthService.signUp(request));
    }

    @Test
    void signUp_성공() {
        SignUpRequest request = new SignUpRequest("test@example.com", "password123", "TestUser", 2116, "B314");
        WashingRoom washingRoom = WashingRoom.B31;

        doNothing().when(authReader).isEmailExist(email);
        when(userReader.getWashingRoomFromRoomNum(request.roomNum())).thenReturn(washingRoom);
        doNothing().when(userCreator).create(request, washingRoom);

        assertDoesNotThrow(() -> commandAuthService.signUp(request));
    }

    @Test
    void login_비밀번호_불일치_InvalidPasswordException_발생() {
        LoginRequest request = new LoginRequest(email, password, deviceToken);

        when(userReader.findUserByEmail(email)).thenReturn(user);
        when(authReader.findTokenByUserAndDeviceToken(user, deviceToken)).thenReturn(token);
        doThrow(new InvalidPasswordException()).when(authValidator).validatePassword(password, encodedPassword);

        assertThrows(InvalidPasswordException.class, () -> commandAuthService.login(request));
    }

    @Test
    void login_성공() {
        LoginRequest request = new LoginRequest(email, password, deviceToken);

        when(userReader.findUserByEmail(email)).thenReturn(user);
        when(authReader.findTokenByUserAndDeviceToken(user, deviceToken)).thenReturn(token);
        doNothing().when(authValidator).validatePassword(password, encodedPassword);
        when(authUpdater.publishToken(user, deviceToken, token)).thenReturn(tokenResponse);

        TokenResponse response = commandAuthService.login(request);

        assertEquals(tokenResponse, response);
    }

    @Test
    void logout_존재하지_않는_토큰_TokenNotFoundException_발생() {
        TokenRefreshRequest request = new TokenRefreshRequest(refreshToken);

        when(authReader.findByRefreshToken(refreshToken)).thenThrow(new TokenNotFoundException());

        assertThrows(TokenNotFoundException.class, () -> commandAuthService.logout(request));
    }

    @Test
    void logout_성공() {
        TokenRefreshRequest request = new TokenRefreshRequest(refreshToken);

        when(authReader.findByRefreshToken(refreshToken)).thenReturn(token);
        doNothing().when(authDeleter).deleteRefreshToken(token);

        assertDoesNotThrow(() -> commandAuthService.logout(request));
    }

    @Test
    void quit_비밀번호_불일치_InvalidPasswordException_발생() {
        QuitRequest request = new QuitRequest(password);

        when(userReader.getUserById(userId)).thenReturn(user);
        doThrow(new InvalidPasswordException()).when(userDeleter).deleteUser(user, password);

        assertThrows(InvalidPasswordException.class, () -> commandAuthService.quit(userId, request));
    }

    @Test
    void quit_성공() {
        QuitRequest request = new QuitRequest(password);

        when(userReader.getUserById(userId)).thenReturn(user);
        doNothing().when(userDeleter).deleteUser(user, password);

        assertDoesNotThrow(() -> commandAuthService.quit(userId, request));
    }

    @Test
    void refresh_존재하지_않는_토큰_TokenNotFoundException_발생() {
        TokenRefreshRequest request = new TokenRefreshRequest(refreshToken);

        when(authReader.findByRefreshToken(refreshToken)).thenThrow(new TokenNotFoundException());

        assertThrows(TokenNotFoundException.class, () -> commandAuthService.refresh(request));
    }

    @Test
    void refresh_성공() {
        TokenRefreshRequest request = new TokenRefreshRequest(refreshToken);

        when(authReader.findByRefreshToken(refreshToken)).thenReturn(token);
        when(authUpdater.refreshToken(token)).thenReturn(tokenResponse);

        TokenResponse response = commandAuthService.refresh(request);

        assertEquals(tokenResponse, response);
    }
}
