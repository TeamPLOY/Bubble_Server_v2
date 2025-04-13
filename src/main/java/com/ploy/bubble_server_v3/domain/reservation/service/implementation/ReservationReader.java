package com.ploy.bubble_server_v3.domain.reservation.service.implementation;

import com.ploy.bubble_server_v3.domain.reservation.domain.Reservation;
import com.ploy.bubble_server_v3.domain.reservation.domain.repository.ReservationRepository;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationReader {
    private final ReservationRepository reservationRepository;

    public Reservation read(Long reservationId) {
        return reservationRepository.getById(reservationId);
    }

    public List<Reservation> readAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> readByUser(Users user) {
        return reservationRepository.findByUser(user);
    }

    public List<Reservation> findByWeek() {
        LocalDate today = LocalDate.now();

        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        LocalDateTime startDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endDateTime = endOfWeek.atTime(LocalTime.MAX);

        return reservationRepository.findByResDateBetween(startDateTime, endDateTime);
    }
}
