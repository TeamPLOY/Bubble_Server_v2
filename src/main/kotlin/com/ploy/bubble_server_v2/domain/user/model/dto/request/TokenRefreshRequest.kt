package com.ploy.bubble_server_v2.domain.user.model.dto.request

import jakarta.validation.constraints.NotBlank

@JvmRecord
data class TokenRefreshRequest(val refreshToken: @NotBlank String?)

