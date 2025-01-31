package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
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

    public void updateStuNum(Users user, Integer newStuNum) {

        user.updateStuNum(newStuNum);

        userRepository.save(user);
    }

    public void updateRoomNum(Users user, String newRoomNum) {
        user.updateRoomNum(newRoomNum);

        String prefix = newRoomNum.substring(0, 1);
        int roomNumber = Integer.parseInt(newRoomNum.substring(1));

            WashingRoom newWashingRoom = WashingRoom.valueOf(WashingRoom.findWashingRoom(prefix, roomNumber));

        user.updateWashingRoom(newWashingRoom);

        userRepository.save(user);
    }


}
