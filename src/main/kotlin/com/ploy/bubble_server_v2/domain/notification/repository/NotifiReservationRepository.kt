package com.ploy.bubble_server_v2.domain.notification.repository


import com.laundering.laundering_server.domain.notification.model.entity.NotifiReservation

interface NotifiReservationRepository : JpaRepository<NotifiReservation?, Long?> {
    fun findByUserIdAndMachine(userId: Long?, machine: String?): java.util.Optional<NotifiReservation?>?
}
