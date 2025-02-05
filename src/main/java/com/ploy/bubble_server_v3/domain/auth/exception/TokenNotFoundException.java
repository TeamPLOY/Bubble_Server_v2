package com.ploy.bubble_server_v3.domain.auth.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class TokenNotFoundException extends BaseException {
    public TokenNotFoundException() {
        super(HttpStatus.NOT_FOUND, "TOKEN_NOT_FOUND", "유효하지 않은 리프레시 토큰입니다.");
    }
}