package com.ploy.bubble_server_v2.domain.washingMachine.model.dto.request

import java.time.LocalDate

@JvmRecord
data class ReservationRequest(
    val date: LocalDate
)
