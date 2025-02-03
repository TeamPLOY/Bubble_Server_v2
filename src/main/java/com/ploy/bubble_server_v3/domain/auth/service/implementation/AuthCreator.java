package com.ploy.bubble_server_v3.domain.auth.service.implementation;

import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.SignUpRequest;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthCreator {

    public Users create(SignUpRequest req, WashingRoom washingRoom) {
        return Users.builder()
                .email(req.email())
                .password(new BCryptPasswordEncoder().encode(req.password()))
                .name(req.name())
                .stuNum(req.stuNum())
                .roomNum(req.roomNum())
                .washingRoom(washingRoom)
                .role(Role.USER)
                .build();
    }
}
