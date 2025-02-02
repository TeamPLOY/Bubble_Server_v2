package com.ploy.bubble_server_v3.domain.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @Schema(description = "이메일")
        @NotBlank String email,

        @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).*$",
                message = "비밀번호에는 최소 하나의 특수문자가 포함되어야 합니다.")
        String password,

        @Schema(description = "이름")
        @NotBlank String name,

        @Schema(description = "학번")
        @NotNull Integer stuNum, // 수정된 부분

        @Schema(description = "방번호")
        @NotBlank String roomNum,

        @Schema(description = "토큰")
        @NotBlank String token // 수정된 부분
) {
}
