package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

class UserUpdaterTest {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private UserUpdater userUpdater;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userUpdater = new UserUpdater(passwordEncoder, usersRepository);
    }

    @Test
    @DisplayName("updatePassword - 사용자의 비밀번호를 업데이트한다")
    void updatePassword_success() {
        // given
        Users user = mock(Users.class);
        String newPassword = "newSecurePassword";
        String encodedPassword = passwordEncoder.encode(newPassword);

        // when
        userUpdater.updatePassword(user, newPassword);

        // then
        verify(user, times(1)).updatePassword(anyString());
        verify(usersRepository, times(1)).save(user);
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
        verify(user, times(1)).updateStuNum(anyInt());
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("updateRoomNum - 사용자의 방 번호와 세탁실을 업데이트한다")
    void updateRoomNum_success() {
        // given
        Users user = mock(Users.class);
        String newRoomNum = "B304";

        // when
        userUpdater.updateRoomNum(user, newRoomNum);

        // then
        verify(user, times(1)).updateRoomNum(newRoomNum);
        verify(user, times(1)).updateWashingRoom(any(WashingRoom.class));
        verify(usersRepository, times(1)).save(user);
    }
}
