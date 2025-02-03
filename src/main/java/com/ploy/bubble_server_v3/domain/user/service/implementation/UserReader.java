package com.ploy.bubble_server_v3.domain.user.service.implementation;

import com.ploy.bubble_server_v3.domain.auth.exception.EmailNotFoundException;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.repository.UsersRepository;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import com.ploy.bubble_server_v3.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReader {

    private final UsersRepository usersRepository;

    public Users findById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public WashingRoom getWashingRoomFromRoomNum(String roomNum) {
        String prefix = roomNum.substring(0, 1);
        int roomNumber = Integer.parseInt(roomNum.substring(1));
        return WashingRoom.findWashingRoom(prefix, roomNumber);
    }

    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(EmailNotFoundException::new);
    }

    public Users getUserById(Long id) {
        return usersRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
