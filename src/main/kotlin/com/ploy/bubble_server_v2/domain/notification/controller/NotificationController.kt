package com.ploy.bubble_server_v2.domain.notification.controller

import com.laundering.laundering_server.domain.facade.NotificationFacade
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.extern.slf4j.Slf4j

@Tag(name = "알림")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
class NotificationController {
    private val notificationFacade: NotificationFacade? = null

    @get:GetMapping
    @get:Operation(summary = "공지사항")
    val notification: ResponseEntity<List<NotificationResponse>>
        get() = ResponseEntity.ok<T>(notificationFacade.getNotification())

    @get:GetMapping("/detail")
    @get:Operation(summary = "공지 세부사항")
    val notificationDetail: ResponseEntity<List<NotificationDetailResponse>>
        get() = ResponseEntity.ok<T>(notificationFacade.getNotificationDetail())

    @get:GetMapping("/history")
    @get:Operation(summary = "예약 사용기록")
    val reservationHistory: ResponseEntity<List<ReservationLogResponse>>
        get() = ResponseEntity.ok<T>(notificationFacade.getReservationHistory(getMemberId()))

    @Operation(summary = "알림 선택")
    @PostMapping("/save")
    fun saveNotification(
        saveNotificationRequest: saveNotificationRequest?
    ): ResponseEntity<Void> {
        notificationFacade.saveNotification(saveNotificationRequest, getMemberId())
        return ResponseEntity.noContent().build<Void>()
    }

    @Operation(summary = "알림 조회")
    @PostMapping("/check")
    fun getResNotification(
        saveNotificationRequest: saveNotificationRequest?
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok<T>(notificationFacade.getResNotification(saveNotificationRequest, getMemberId()))
    }
}
