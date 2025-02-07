package com.ploy.bubble_server_v3.domain.user.service;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import com.ploy.bubble_server_v3.domain.user.exception.InvalidCurrentPasswordException;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdatePasswordRequest;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserReader;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserUpdater;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandUserServiceTest {

    @Mock
    private UserReader userReader;

    @Mock
    private UserUpdater userUpdater;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private CommandUserService commandUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("비밀번호 업데이트 성공")
    void updatePassword_success() {
        // given
        Long userId = 1L;
        String currentPassword = "oldPassword";
        String newPassword = "newPassword123";
        UpdatePasswordRequest request = new UpdatePasswordRequest(currentPassword, newPassword);
        Users user = new Users(1L, "hashedOldPassword", "한태영", 1234, "test@example.com", "B304", WashingRoom.B31, Role.USER);

        when(userReader.findById(userId)).thenReturn(user);

        // when
        commandUserService.updatePassword(userId, request);

        // then
        verify(userValidator, times(1)).validateCurrentPassword(user, currentPassword);
        verify(userUpdater, times(1)).updatePassword(user, newPassword);
    }

    @Test
    @DisplayName("비밀번호 업데이트 실패 - 현재 비밀번호 틀림")
    void updatePassword_fail_invalidCurrentPassword() {
        // given
        Long userId = 1L;
        String currentPassword = "wrongPassword";
        String newPassword = "newPassword123";
        UpdatePasswordRequest request = new UpdatePasswordRequest(currentPassword, newPassword);
        Users user = new Users(1L, "hashedOldPassword", "한태영", 1234, "test@example.com", "B304", WashingRoom.B31, Role.USER);

        when(userReader.findById(userId)).thenReturn(user);
        doThrow(new InvalidCurrentPasswordException()).when(userValidator).validateCurrentPassword(user, currentPassword);

        // when & then
        assertThrows(InvalidCurrentPasswordException.class, () -> commandUserService.updatePassword(userId, request));

        verify(userValidator, times(1)).validateCurrentPassword(user, currentPassword);
        verify(userUpdater, never()).updatePassword(any(), any());
    }
}
