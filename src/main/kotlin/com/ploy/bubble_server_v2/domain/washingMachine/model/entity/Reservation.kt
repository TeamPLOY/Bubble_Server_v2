package com.ploy.bubble_server_v2.domain.washingMachine.model.entity

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
@NoArgsConstructor
class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    private val userId: Long? = null

    private val isCancel = false

    private val date: LocalDate? = null

    private val washingRoom: String? = null
}
