package com.ploy.bubble_server_v3.domain.reservation.presentation.dto.response;

import com.ploy.bubble_server_v3.domain.reservation.domain.Reservation;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        Long userId,
        Boolean cancel,
        LocalDateTime date,
        LocalDateTime resDate,
        Integer unit
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getCancel(),
                reservation.getDate(),
                reservation.getResDate(),
                reservation.getUnit()
        );
    }
}
