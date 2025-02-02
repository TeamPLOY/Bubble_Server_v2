package com.ploy.bubble_server_v3.domain.user.service;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdatePasswordRequest;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdateRoomNumRequest;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdateStuNumRequest;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserReader;
import com.ploy.bubble_server_v3.domain.user.service.implementation.UserUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandUserService {

    private final UserReader userReader;
    private final UserUpdater userUpdater;

    public void updatePassword(Long id, UpdatePasswordRequest request) {
        Users user = userReader.findById(id);
        userUpdater.updatePassword(user, request.password());
    }

    public void updateStuNum(Long id, UpdateStuNumRequest request) {
        Users user = userReader.findById(id);
        userUpdater.updateStuNum(user, request.stuNum());
    }

    public void updateRoomNum(Long id, UpdateRoomNumRequest request) {
        Users user = userReader.findById(id);
        WashingRoom newWashingRoom = userReader.getWashingRoomFromRoomNum(request.roomNum());
        userUpdater.updateRoomNum(user, request.roomNum(),newWashingRoom);
    }
}
