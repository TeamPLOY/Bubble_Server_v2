package com.ploy.bubble_server_v3.domain.reservation.service.implementation;

import com.ploy.bubble_server_v3.domain.reservation.domain.Reservation;
import com.ploy.bubble_server_v3.domain.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationUpdater {
    private final ReservationRepository reservationRepository;

    public void update(Reservation updatableReservation, Reservation reservation) {
        updatableReservation.update(reservation);
    }

    public void cancel(Reservation reservation) {
        reservation.cancel();
    }
}
