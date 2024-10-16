package com.ploy.bubble_server_v2.domain.user.model.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Getter
import lombok.Setter

@Getter
@Setter
class LoginRequest {
    @Schema(description = "이메일")
    private val email: String? = null

    @Schema(description = "패스워드")
    private val password: String? = null
}