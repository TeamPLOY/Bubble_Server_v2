package com.ploy.bubble_server_v3.domain.auth.service;

import com.ploy.bubble_server_v3.common.jwt.dto.TokenResponse;
import com.ploy.bubble_server_v3.domain.auth.domain.Token;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.LoginRequest;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.QuitRequest;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.SignUpRequest;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.TokenRefreshRequest;
import com.ploy.bubble_server_v3.domain.auth.service.implementation.*;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserDeleter;
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
    private final AuthDeleter authDeleter;
    private final UserReader userReader;
    private final UserDeleter userDeleter;

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

    public void logout(TokenRefreshRequest request){
        Token existingToken = authReader.findByRefreshToken(request.refreshToken());
        authDeleter.deleteRefreshToken(existingToken);
    }

    public void quit(Long id,QuitRequest request){
        Users user = userReader.getUserById(id);
        userDeleter.deleteUser(user, request.password());
    }
}
