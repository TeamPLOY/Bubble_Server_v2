package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import com.ploy.bubble_server_v3.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserReaderTest {

    private UsersRepository usersRepository;
    private UserReader userReader;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        userReader = new UserReader(usersRepository);
    }

    @Test
    void findById_success() {
        // given
        Users user = new Users(1L, "hashedPassword", "한태영", 1234, "test@example.com", "B304", WashingRoom.B31, Role.USER);
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        Users foundUser = userReader.findById(1L);

        // then
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("한태영", foundUser.getName());
    }

    @Test
    void findById_userNotFound() {
        // given
        when(usersRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(UserNotFoundException.class, () -> userReader.findById(99L));
    }

    @Test
    void getWashingRoomFromRoomNum_validRoom() {
        // when
        WashingRoom washingRoom = userReader.getWashingRoomFromRoomNum("B304");

        // then
        assertNotNull(washingRoom);
        assertEquals(WashingRoom.B31, washingRoom); // 기대하는 WashingRoom 값을 정확히 확인해야 함
    }

    @Test
    void getWashingRoomFromRoomNum_invalidRoom() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> userReader.getWashingRoomFromRoomNum("X999"));
    }
}
