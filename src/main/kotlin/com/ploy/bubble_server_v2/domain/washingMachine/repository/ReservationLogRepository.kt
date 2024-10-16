package com.ploy.bubble_server_v2.domain.washingMachine.repository

import com.ploy.bubble_server_v2.domain.washingMachine.model.entity.ReservationLog

import org.springframework.data.jpa.repository.JpaRepository

interface ReservationLogRepository : JpaRepository<ReservationLog?, Long?> {
    fun findByUserId(userId: Long?): List<ReservationLog?>?
}


