package com.ploy.bubble_server_v3.domain.reservation.presentation.dto.request;

import com.ploy.bubble_server_v3.domain.reservation.domain.Reservation;

import java.time.LocalDateTime;

public record CreateReservationRequest(
        LocalDateTime resDate,
        Integer unit
) {
    public Reservation toEntity() {
        return Reservation.builder()
                .cancel(false)
                .resDate(resDate)
                .unit(unit)
                .build();
    }
}
