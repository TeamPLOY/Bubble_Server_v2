package com.ploy.bubble_server_v2.domain.washingMachine.repository


import com.laundering.laundering_server.domain.washingMachine.model.entity.Reservation

interface ReservationRepository : JpaRepository<Reservation?, Long?> {
    fun findByUserIdAndDate(userId: Long?, date: java.time.LocalDate?): java.util.Optional<Reservation?>?
    fun findByWashingRoomAndDateBetween(
        washingRoom: String?,
        startDate: java.time.LocalDate?,
        endDate: java.time.LocalDate?
    ): List<Reservation?>?

    fun findByUserIdAndDateBetween(
        userId: Long?,
        startDate: java.time.LocalDate?,
        endDate: java.time.LocalDate?
    ): List<Reservation?>?

    fun findByUserId(userId: Long?): List<Reservation?>?
    fun findByUserIdAndDateAndIsCancelFalse(
        userId: Long?,
        date: java.time.LocalDate?
    ): java.util.Optional<Reservation?>?
}