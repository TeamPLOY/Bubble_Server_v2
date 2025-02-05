package com.ploy.bubble_server_v3.domain.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(description = "이메일")
        @NotBlank String email,
        @Schema(description = "비밀번호")
        @NotBlank String password,
        @Schema(description = "기기 토큰")
        @NotBlank String deviceToken
        ) {
}
