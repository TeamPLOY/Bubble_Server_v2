package com.ploy.bubble_server_v3.domain.reservation.service.implementation;

import com.ploy.bubble_server_v3.domain.reservation.domain.Reservation;
import com.ploy.bubble_server_v3.domain.reservation.domain.repository.ReservationRepository;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationCreator {
    private final ReservationRepository reservationRepository;

    public void create(Reservation reservation, Users user) {
        reservation.updateUser(user);
        reservationRepository.save(reservation);
    }
}
