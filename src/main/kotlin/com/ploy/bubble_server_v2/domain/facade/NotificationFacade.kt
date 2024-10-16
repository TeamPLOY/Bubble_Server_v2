package com.ploy.bubble_server_v2.domain.facade

import com.ploy.bubble_server_v2.domain.notification.model.dto.request.saveNotificationRequest
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@RequiredArgsConstructor
class NotificationFacade {
    private val notificationService: NotificationService? = null

    @get:Transactional
    val notification: List<Any>
        get() = notificationService.getNotification()

    @get:Transactional
    val notificationDetail: List<Any>
        get() = notificationService.getNotificationDetail()

    @Transactional
    fun getReservationHistory(id: Long?): List<ReservationLogResponse> {
        return notificationService.getReservationHistory(id)
    }

    @Transactional
    fun saveNotification(request: saveNotificationRequest?, id: Long?) {
        notificationService.saveNotification(request, id)
    }

    @Transactional
    fun getResNotification(request: saveNotificationRequest?, id: Long?): Boolean {
        return notificationService.getResNotification(request, id)
    }
}
