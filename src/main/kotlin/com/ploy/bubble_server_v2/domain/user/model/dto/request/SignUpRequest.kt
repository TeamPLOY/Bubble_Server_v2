package com.ploy.bubble_server_v2.domain.user.model.dto.request

@JvmRecord
data class SignUpRequest(
    val email: String,

    val password: String,

    val name: String,

    val stuNum: Int,

    val roomNum: String
)
