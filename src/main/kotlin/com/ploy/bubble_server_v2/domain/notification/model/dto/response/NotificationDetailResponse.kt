package com.ploy.bubble_server_v2.domain.notification.model.dto.response

import java.time.LocalDate

@JvmRecord
data class NotificationDetailResponse(
    val title: String,
    val detail: String,
    val date: LocalDate
)
