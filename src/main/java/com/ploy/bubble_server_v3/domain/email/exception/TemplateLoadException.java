package com.ploy.bubble_server_v3.domain.email.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class TemplateLoadException extends BaseException {
    public TemplateLoadException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "TEMPLATE_LOAD_FAILED", "템플릿 파일을 로드하는 데 실패했습니다.");
    }
}