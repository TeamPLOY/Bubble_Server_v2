package com.ploy.bubble_server_v3.domain.machine.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class OAuthSignatureGenerationException extends BaseException {
    public OAuthSignatureGenerationException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "OAUTH_SIGNATURE_GENERATION_FAILED", "OAuth 서명 생성에 실패했습니다.");
    }
}
