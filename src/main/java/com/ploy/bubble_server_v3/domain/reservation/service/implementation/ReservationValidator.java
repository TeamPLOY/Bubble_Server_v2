package com.ploy.bubble_server_v3.domain.reservation.service.implementation;

import com.ploy.bubble_server_v3.domain.reservation.exception.NotMatchReservationOwnerException;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationValidator {

    public void shouldBeSameUser(Users owner, Users user) {
        if (!owner.getId().equals(user.getId())) {
            throw new NotMatchReservationOwnerException();
        }
    }
}
