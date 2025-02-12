package com.ploy.bubble_server_v3.domain.email.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidEmailFormatException extends BaseException {
    public InvalidEmailFormatException() {
        super(HttpStatus.BAD_REQUEST, "INVALID_EMAIL_FORMAT", "유효하지 않은 이메일 형식입니다.");
    }
}