package com.ploy.bubble_server_v2.domain.user.model.entity

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
class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null // PK

    private val email: String? = null

    private val code: Int? = null

    private val date: LocalDate? = null
}
