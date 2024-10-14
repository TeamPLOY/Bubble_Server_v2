package com.ploy.bubble_server_v2.common.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
