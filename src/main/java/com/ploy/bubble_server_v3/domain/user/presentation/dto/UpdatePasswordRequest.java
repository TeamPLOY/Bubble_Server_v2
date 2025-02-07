package com.ploy.bubble_server_v3.domain.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(
        @Schema(description = "현재 비밀번호")
        @NotBlank String currentPassword,
        @Schema(description = "변경할 비밀번호")
        @NotBlank String newPassword
) {
}
