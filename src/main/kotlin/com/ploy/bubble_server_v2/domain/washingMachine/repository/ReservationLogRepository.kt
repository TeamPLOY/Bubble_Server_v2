package com.ploy.bubble_server_v2.domain.washingMachine.repository


import com.laundering.laundering_server.domain.washingMachine.model.entity.ReservationLog

interface ReservationLogRepository : JpaRepository<ReservationLog?, Long?> {
    fun findByUserId(userId: Long?): List<ReservationLog?>?
}


