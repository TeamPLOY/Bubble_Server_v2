package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

class UserUpdaterTest {

    private PasswordEncoder passwordEncoder;
    private UserUpdater userUpdater;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        userUpdater = new UserUpdater(passwordEncoder);
    }

    @Test
    @DisplayName("updatePassword - 사용자의 비밀번호를 업데이트한다")
    void updatePassword_success() {
        // given
        Users user = mock(Users.class);
        String newPassword = "newSecurePassword";

        // when
        userUpdater.updatePassword(user, newPassword);

        // then
        verify(user, times(1)).updatePassword(anyString());
    }

    @Test
    @DisplayName("updateStuNum - 사용자의 학번을 업데이트한다")
    void updateStuNum_success() {
        // given
        Users user = mock(Users.class);
        Integer newStuNum = 2116;

        // when
        userUpdater.updateStuNum(user, newStuNum);

        // then
        verify(user, times(1)).updateStuNum(newStuNum);
    }

    @Test
    @DisplayName("updateRoomNum - 사용자의 방 번호와 세탁실을 업데이트한다")
    void updateRoomNum_success() {
        // given
        Users user = mock(Users.class);
        String newRoomNum = "B304";
        WashingRoom newWashingRoom = WashingRoom.B31;

        // when
        userUpdater.updateRoomNum(user, newRoomNum, newWashingRoom);

        // then
        verify(user, times(1)).updateRoomNum(newRoomNum);
        verify(user, times(1)).updateWashingRoom(newWashingRoom);
    }
}
