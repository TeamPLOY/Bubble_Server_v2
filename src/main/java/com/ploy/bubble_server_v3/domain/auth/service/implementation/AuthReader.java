package com.ploy.bubble_server_v3.domain.auth.service.implementation;

import com.ploy.bubble_server_v3.domain.auth.exception.EmailAlreadyExistsException;
import com.ploy.bubble_server_v3.domain.auth.exception.EmailNotFoundException;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthReader {
    private final UsersRepository usersRepository;

    public boolean isEmailExist(String email) {
        if (usersRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }
        return false;
    }

    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(EmailNotFoundException::new);
    }
}
