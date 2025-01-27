package com.ploy.bubble_server_v3.common.exception;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class BaseExceptionTest {

    @Test
    void BaseException_초기화_테스트() {
        // given
        HttpStatus status = HttpStatus.NOT_FOUND;
        String errorCode = "USER_NOT_FOUND";
        String message = "유저를 찾을 수 없습니다.";

        // when
        BaseException exception = new BaseException(status, errorCode, message);

        // then
        assertEquals(status, exception.getStatus(), "HttpStatus가 올바르게 설정되어야 합니다.");
        assertEquals(errorCode, exception.getErrorCode(), "에러 코드가 올바르게 설정되어야 합니다.");
        assertEquals(message, exception.getMessage(), "메시지가 올바르게 설정되어야 합니다.");
    }
}

