package com.ploy.bubble_server_v2.domain.washingMachine.model.dto.response

import java.time.LocalDate

@JvmRecord
data class ReservationSummaryResponse(
    val date: LocalDate,
    val day: String,
    val userCount: Long
)
