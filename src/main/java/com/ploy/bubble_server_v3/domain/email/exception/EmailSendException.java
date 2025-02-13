package com.ploy.bubble_server_v3.domain.email.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmailSendException extends BaseException {
    public EmailSendException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL_SEND_FAILED", "이메일 전송에 실패했습니다.");
    }
}