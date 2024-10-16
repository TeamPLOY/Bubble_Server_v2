package com.ploy.bubble_server_v2.domain.washingMachine.model.dto.response

import java.time.LocalDate

@JvmRecord
data class ReservationSummaryResponse(
    val date: LocalDate,  // 날짜를 LocalDate로 사용 (시간 제외)
    val day: String,  // 무슨 요일인지
    val userCount: Long // 예약한 유저 수
)
