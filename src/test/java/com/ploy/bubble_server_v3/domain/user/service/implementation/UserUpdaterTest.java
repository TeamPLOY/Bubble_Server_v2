package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
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
    void updatePassword_success() {
        // given
        Users user = mock(Users.class);
        String newPassword = "newSecurePassword";
        String encodedPassword = passwordEncoder.encode(newPassword);

        // when
        userUpdater.updatePassword(user, newPassword);

        // then
        verify(user, times(1)).updatePassword(anyString());  // updatePassword가 1번 호출되었는지 확인
        verify(usersRepository, times(1)).save(user);        // save가 1번 호출되었는지 확인
    }

    @Test
    void updateStuNum_success() {
        // given
        Users user = mock(Users.class);
        Integer newStuNum = 2116;

        // when
        userUpdater.updateStuNum(user, newStuNum);

        // then
        verify(user, times(1)).updateStuNum(anyInt());  // updateStuNum이 1번 호출되었는지 확인
        verify(usersRepository, times(1)).save(user);   // save가 1번 호출되었는지 확인
    }
}
