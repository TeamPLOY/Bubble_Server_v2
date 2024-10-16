package com.ploy.bubble_server_v2.domain.washingMachine.controller


import com.ploy.bubble_server_v2.common.util.AuthenticationUtil.getMemberId
import com.ploy.bubble_server_v2.domain.facade.WashingMachineFacade
import com.ploy.bubble_server_v2.domain.washingMachine.model.dto.request.ReservationRequest
import com.ploy.bubble_server_v2.domain.washingMachine.model.dto.response.ReservationSummaryResponse
import com.ploy.bubble_server_v2.domain.washingMachine.model.dto.response.WashingMachineResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "세탁기")
@Slf4j
@RestController
@RequiredArgsConstructor
class WashingMachineController {
    private val washingMachineFacade: WashingMachineFacade? = null

    @get:GetMapping("/washing")
    @get:Operation(summary = "세탁기 시간 조회")
    val status: ResponseEntity<List<WashingMachineResponse>>
        get() {
            val ws: List<WashingMachineResponse> = washingMachineFacade.getStatus(getMemberId())
            return ResponseEntity.ok<List<WashingMachineResponse>>(ws)
        }

    @Operation(summary = "세탁기 예약")
    @PostMapping("/reservation")
    fun reservation(
        @RequestBody reservationRequest: ReservationRequest
    ): ResponseEntity<Void> {
        washingMachineFacade.reservation(getMemberId(), reservationRequest.date())
        return ResponseEntity.noContent().build<Void>()
    }

    @Operation(summary = "세탁기 예약 취소")
    @PostMapping("/reservation/cancel")
    fun cancelReservation(
        @RequestBody reservationRequest: ReservationRequest
    ): ResponseEntity<Void> {
        washingMachineFacade.cancelReservation(getMemberId(), reservationRequest.date)
        return ResponseEntity.noContent().build<Void>()
    }

    @get:GetMapping("/reservation")
    @get:Operation(summary = "세탁기 예약 상태 조회")
    val reservation: ResponseEntity<List<ReservationSummaryResponse>>
        get() {
            val reservations: List<ReservationSummaryResponse> =
                washingMachineFacade.getReservation(getMemberId())
            return ResponseEntity.ok<List<ReservationSummaryResponse>>(reservations)
        }

    @get:GetMapping("/isReserved")
    @get:Operation(summary = "당주 예약 여부 확인")
    val isReserved: ResponseEntity<Boolean>
        get() {
            val IsReserved: Boolean = washingMachineFacade.getIsReserved(getMemberId())
            return ResponseEntity.ok<Boolean>(IsReserved)
        }
}
