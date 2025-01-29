package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdater {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository userRepository;

    public void updatePassword(Users user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.updatePassword(encodedPassword);

        userRepository.save(user);
    }
}
