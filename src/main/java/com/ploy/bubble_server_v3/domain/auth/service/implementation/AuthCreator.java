package com.ploy.bubble_server_v3.domain.auth.service.implementation;

import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.SignUpRequest;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCreator {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public Users create(SignUpRequest req, WashingRoom washingRoom) {
        Users user = Users.builder()
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .name(req.name())
                .stuNum(req.stuNum())
                .roomNum(req.roomNum())
                .washingRoom(washingRoom)
                .role(Role.USER)
                .build();

        return usersRepository.save(user);
    }
}
