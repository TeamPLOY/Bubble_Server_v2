package com.ploy.bubble_server_v2.domain.notification.model.dto.response

import java.time.LocalDate

@JvmRecord
data class ReservationLogResponse(
    val date: LocalDate,
    val cancel: Boolean,
    val washingRoom: String
)
