package com.ploy.bubble_server_v2.domain.user.model.dto.request

@JvmRecord
data class EmailCheckRequest(
    val code: Int,
    val email: String
)
