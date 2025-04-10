package com.ploy.bubble_server_v3.domain.machine.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AccessTokenRefreshException extends BaseException {
    public AccessTokenRefreshException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "ACCESS_TOKEN_REFRESH_FAILED", "액세스 토큰 갱신 중 오류가 발생했습니다.");
    }
}
