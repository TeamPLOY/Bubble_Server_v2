package com.ploy.bubble_server_v2.domain.washingMachine.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
class ReservationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    private val userId: Long? = null // 예약한 사람

    private val isCancel = false // 예약 or 취소

    private val date: LocalDateTime? = null // 예약한 날짜

    private val resDate: LocalDate? = null // 예약할 날짜

    private val washingRoom: String? = null // 세탁실 위치
}
