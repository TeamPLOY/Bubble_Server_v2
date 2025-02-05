package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.SignUpRequest;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCreatorTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserCreator userCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 회원가입_유저_생성_테스트() {
        // Given
        SignUpRequest request = new SignUpRequest("test@example.com", "password123", "TestUser", 2116, "B314");
        WashingRoom washingRoom = WashingRoom.B31;

        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.encode(request.password())).thenReturn(encodedPassword);

        Users savedUser = Users.builder()
                .email(request.email())
                .password(encodedPassword)
                .name(request.name())
                .stuNum(request.stuNum())
                .roomNum(request.roomNum())
                .washingRoom(washingRoom)
                .role(Role.USER)
                .build();
        when(usersRepository.save(any(Users.class))).thenReturn(savedUser);

        // When
        Users createdUser = userCreator.create(request, washingRoom);

        // Then
        assertNotNull(createdUser);
        assertEquals(request.email(), createdUser.getEmail());
        assertEquals(encodedPassword, createdUser.getPassword());
        assertEquals(request.name(), createdUser.getName());
        assertEquals(request.stuNum(), createdUser.getStuNum());
        assertEquals(request.roomNum(), createdUser.getRoomNum());
        assertEquals(washingRoom, createdUser.getWashingRoom());
        assertEquals(Role.USER, createdUser.getRole());

        // Verify
        ArgumentCaptor<Users> userCaptor = ArgumentCaptor.forClass(Users.class);
        verify(usersRepository, times(1)).save(userCaptor.capture());

        Users capturedUser = userCaptor.getValue();
        assertEquals(request.email(), capturedUser.getEmail());
        assertEquals(encodedPassword, capturedUser.getPassword());
    }
}
