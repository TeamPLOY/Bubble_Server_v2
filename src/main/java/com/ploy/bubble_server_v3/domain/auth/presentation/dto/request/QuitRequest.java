package com.ploy.bubble_server_v3.domain.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record QuitRequest(
        @Schema(description = "비밀번호")
        @NotBlank String password
) {
}
