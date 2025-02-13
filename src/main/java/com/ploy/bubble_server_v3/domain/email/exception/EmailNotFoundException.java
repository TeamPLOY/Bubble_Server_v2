package com.ploy.bubble_server_v3.domain.email.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends BaseException {
    public EmailNotFoundException() {
        super(HttpStatus.NOT_FOUND, "EMAIL_NOT_FOUND", "이메일 인증 코드를 찾을 수 없습니다.");
    }
}