package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.auth.exception.InvalidPasswordException;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeleter {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public void deleteUser(Users user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        usersRepository.delete(user);
    }
}
