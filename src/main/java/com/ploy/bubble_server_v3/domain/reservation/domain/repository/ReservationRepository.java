package com.ploy.bubble_server_v3.domain.reservation.domain.repository;

import com.ploy.bubble_server_v3.domain.reservation.domain.Reservation;
import com.ploy.bubble_server_v3.domain.reservation.exception.ReservationNotFoundException;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(Users user);

    List<Reservation> findByResDateBetween(LocalDateTime start, LocalDateTime end);

    default Reservation getById(Long reservationId) {
        return findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }
}
