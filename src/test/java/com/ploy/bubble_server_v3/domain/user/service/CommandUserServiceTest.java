package com.ploy.bubble_server_v3.domain.user.service;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdatePasswordRequest;
import com.ploy.bubble_server_v3.domain.user.service.CommandUserService;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserReader;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class CommandUserServiceTest {

    @Mock
    private UserReader userReader;

    @Mock
    private UserUpdater userUpdater;

    @InjectMocks
    private CommandUserService commandUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Mockito 초기화
    }

    @Test
    void updatePassword_success() {
        // given
        Long userId = 1L;
        String newPassword = "newPassword123";
        UpdatePasswordRequest request = new UpdatePasswordRequest(newPassword);
        Users user = new Users(1L, "hashedPassword", "한태영", 1234, "test@example.com", "B304", WashingRoom.B31, Role.USER);

        when(userReader.findById(userId)).thenReturn(user);

        // when
        commandUserService.updatePassword(userId, request);

        // then
        verify(userUpdater, times(1)).updatePassword(user, newPassword);  // updatePassword가 1번 호출되었는지 확인
    }
}
