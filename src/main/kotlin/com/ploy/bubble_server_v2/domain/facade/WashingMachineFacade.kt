package com.ploy.bubble_server_v2.domain.facade

import com.ploy.bubble_server_v2.domain.washingMachine.model.dto.response.ReservationSummaryResponse
import com.ploy.bubble_server_v2.domain.washingMachine.model.dto.response.WashingMachineResponse
import lombok.RequiredArgsConstructor

@org.springframework.stereotype.Component
@RequiredArgsConstructor
class WashingMachineFacade {
    private val washingMachineService: WashingMachineService? = null
    private val reservationService: ReservationService? = null

    @org.springframework.transaction.annotation.Transactional
    fun getStatus(id: Long?): List<WashingMachineResponse> {
        return washingMachineService.getStatus(id)
    }

    @org.springframework.transaction.annotation.Transactional
    fun reservation(id: Long?, date: java.time.LocalDate?) {
        reservationService.reservation(id, date)
    }

    @org.springframework.transaction.annotation.Transactional
    fun cancelReservation(id: Long?, date: java.time.LocalDate?) {
        reservationService.cancelReservation(id, date)
    }

    @org.springframework.transaction.annotation.Transactional
    fun getReservation(id: Long?): List<ReservationSummaryResponse> {
        return reservationService.getReservation(id)
    }

    fun getIsReserved(id: Long?): Boolean {
        return reservationService.getIsReserved(id)
    }
}

