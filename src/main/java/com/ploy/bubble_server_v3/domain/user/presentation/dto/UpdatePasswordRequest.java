package com.ploy.bubble_server_v3.domain.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePasswordRequest(
        @Schema(description = "변경할 비밀번호")
        String password
) {
}
