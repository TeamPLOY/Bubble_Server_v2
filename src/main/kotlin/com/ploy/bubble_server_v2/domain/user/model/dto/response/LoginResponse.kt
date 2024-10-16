package com.ploy.bubble_server_v2.domain.user.model.dto.response

@JvmRecord
data class LoginResponse(
    val tokens: TokenResponse
)
