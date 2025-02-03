package com.ploy.bubble_server_v3.domain.auth.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends BaseException {
    public EmailNotFoundException() {
        super(HttpStatus.NOT_FOUND, "EMAIL_NOT_FOUND", "이메일을 찾을 수 없습니다.");
    }
}
