package com.ploy.bubble_server_v3.domain.user.service;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdatePasswordRequest;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdateStuNumRequest;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdateRoomNumRequest;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserReader;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("비밀번호 업데이트 성공")
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
        verify(userUpdater, times(1)).updatePassword(user, newPassword);
    }

    @Test
    @DisplayName("학번 업데이트 성공")
    void updateStuNum_success() {
        // given
        Long userId = 1L;
        Integer newStuNum = 2116;
        UpdateStuNumRequest request = new UpdateStuNumRequest(newStuNum);
        Users user = new Users(1L, "hashedPassword", "한태영", 1234, "test@example.com", "B304", WashingRoom.B31, Role.USER);

        when(userReader.findById(userId)).thenReturn(user);

        // when
        commandUserService.updateStuNum(userId, request);

        // then
        verify(userUpdater, times(1)).updateStuNum(user, newStuNum);
    }

    @Test
    @DisplayName("방 번호 업데이트 성공")
    void updateRoomNum_success() {
        // given
        Long userId = 1L;
        String newRoomNum = "B402";
        UpdateRoomNumRequest request = new UpdateRoomNumRequest(newRoomNum);
        Users user = new Users(1L, "hashedPassword", "한태영", 1234, "test@example.com", "B304", WashingRoom.B31, Role.USER);
        WashingRoom newWashingRoom = WashingRoom.B42;

        when(userReader.findById(userId)).thenReturn(user);
        when(userReader.getWashingRoomFromRoomNum(newRoomNum)).thenReturn(newWashingRoom);

        // when
        commandUserService.updateRoomNum(userId, request);

        // then
        verify(userUpdater, times(1)).updateRoomNum(user, newRoomNum, newWashingRoom);
    }
}
