package com.ploy.bubble_server_v3.domain.email.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CodeMismatchException extends BaseException {
    public CodeMismatchException() {
        super(HttpStatus.BAD_REQUEST, "CODE_MISMATCH", "인증 코드가 일치하지 않습니다.");
    }
}