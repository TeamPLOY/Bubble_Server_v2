package com.ploy.bubble_server_v3.domain.user.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidCurrentPasswordException extends BaseException {
    public InvalidCurrentPasswordException() {
        super(HttpStatus.BAD_REQUEST, "INVALID_CURRENT_PASSWORD", "현재 비밀번호가 올바르지 않습니다.");
    }
}
