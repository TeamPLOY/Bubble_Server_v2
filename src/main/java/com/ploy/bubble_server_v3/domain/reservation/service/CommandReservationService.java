package com.ploy.bubble_server_v3.domain.reservation.service;

import com.ploy.bubble_server_v3.domain.reservation.domain.Reservation;
import com.ploy.bubble_server_v3.domain.reservation.service.implementation.*;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandReservationService {
    private final ReservationCreator reservationCreator;
    private final ReservationReader reservationReader;
    private final ReservationUpdater reservationUpdater;
    private final ReservationValidator reservationValidator;

    public void create(Reservation reservation, Users user) {
        reservationCreator.create(reservation, user);
    }

    public void update(Long reservationId, Reservation reservation, Users user) {
        Reservation updatableReservation = reservationReader.read(reservationId);
        reservationValidator.shouldBeSameUser(updatableReservation.getUser(), user);
        reservationUpdater.update(updatableReservation, reservation);
    }

    public void cancel(Long reservationId, Users user) {
        Reservation deletableReservation = reservationReader.read(reservationId);
        reservationValidator.shouldBeSameUser(deletableReservation.getUser(), user);
        reservationUpdater.cancel(deletableReservation);
    }
}
