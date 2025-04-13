package com.ploy.bubble_server_v3.domain.reservation.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends BaseException {
    public ReservationNotFoundException(Long reservationId) {
        super(HttpStatus.NOT_FOUND, "RESERVATION_NOT_FOUND", reservationId + "인 예약이 존재하지 않습니다.");
    }
}
