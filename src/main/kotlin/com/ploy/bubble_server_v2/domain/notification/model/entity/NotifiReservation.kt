package com.ploy.bubble_server_v2.domain.notification.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.*
import java.time.LocalDate

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
class NotifiReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null // PK

    private val userId: Long? = null

    private val machine: String? = null

    private val date: LocalDate? = null
}