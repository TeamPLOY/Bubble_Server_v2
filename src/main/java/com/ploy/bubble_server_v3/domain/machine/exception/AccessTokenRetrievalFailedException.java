package com.ploy.bubble_server_v3.domain.machine.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AccessTokenRetrievalFailedException extends BaseException {
    public AccessTokenRetrievalFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "ACCESS_TOKEN_RETRIEVAL_FAILED", "액세스 토큰을 받아오지 못했습니다.");
    }
}
