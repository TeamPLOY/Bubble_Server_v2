package com.ploy.bubble_server_v3.domain.auth.service;

import com.ploy.bubble_server_v3.common.jwt.dto.TokenResponse;
import com.ploy.bubble_server_v3.domain.auth.domain.Token;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.LoginRequest;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.SignUpRequest;
import com.ploy.bubble_server_v3.domain.auth.service.implementation.AuthCreator;
import com.ploy.bubble_server_v3.domain.auth.service.implementation.AuthReader;
import com.ploy.bubble_server_v3.domain.auth.service.implementation.AuthUpdater;
import com.ploy.bubble_server_v3.domain.auth.service.implementation.AuthValidator;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandAuthService {

    private final AuthReader authReader;
    private final AuthCreator authCreator;
    private final AuthUpdater authUpdater;
    private final AuthValidator authValidator;
    private final UserReader userReader;

    public void signUp(SignUpRequest request) {
        authReader.isEmailExist(request.email());
        WashingRoom washingRoom = userReader.getWashingRoomFromRoomNum(request.roomNum());
        authCreator.create(request, washingRoom);
    }

    public TokenResponse login(LoginRequest request){
        Users user = userReader.findUserByEmail(request.email());

        Token existingToken = authReader.findTokenByUserAndDeviceToken(user, request.deviceToken());

        authValidator.validatePassword(request.password(), user.getPassword());

        return authUpdater.publishToken(user, request.deviceToken(), existingToken);
    }
}
