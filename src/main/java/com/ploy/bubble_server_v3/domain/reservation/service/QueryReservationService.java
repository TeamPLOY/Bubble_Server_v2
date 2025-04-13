package com.ploy.bubble_server_v3.domain.reservation.service;

import com.ploy.bubble_server_v3.domain.reservation.domain.Reservation;
import com.ploy.bubble_server_v3.domain.reservation.service.implementation.ReservationReader;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryReservationService {
    private final ReservationReader reservationReader;

    public Reservation readOne(Long reservationId) {
        return reservationReader.read(reservationId);
    }

    public List<Reservation> readAll() {
        return reservationReader.readAll();
    }

    public List<Reservation> readByUser(Users user) {
        return reservationReader.readByUser(user);
    }

    public List<Reservation> findByWeek() {
        return reservationReader.findByWeek();
    }
}
