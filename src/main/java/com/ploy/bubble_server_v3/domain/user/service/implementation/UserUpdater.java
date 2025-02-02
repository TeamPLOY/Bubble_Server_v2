package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdater {

    private final PasswordEncoder passwordEncoder;

    public void updatePassword(Users user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.updatePassword(encodedPassword);
    }

    public void updateStuNum(Users user, Integer newStuNum) {
        user.updateStuNum(newStuNum);
    }

    public void updateRoomNum(Users user, String newRoomNum, WashingRoom newWashingRoom) {
        user.updateRoomNum(newRoomNum);
        user.updateWashingRoom(newWashingRoom);
    }
}
