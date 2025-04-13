package com.ploy.bubble_server_v3.domain.reservation.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NotMatchReservationOwnerException extends BaseException {
    public NotMatchReservationOwnerException() {
        super(HttpStatus.FORBIDDEN, "RESERVATION_OWNER_NOT_MATCH", "예약한 유저가 아닙니다.");
    }
}
