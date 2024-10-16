package com.ploy.bubble_server_v2.domain.user.model.entity

import jakarta.persistence.*
import lombok.*

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null // PK

    private val refreshToken: String? = null

    private val password: String? = null //비밀번호

    private val name: String? = null // 이름

    private val stuNum: Int? = null // 학번

    @Column(unique = true)
    private var email: String? = null // 이메일

    private val roomNum: String? = null // 기숙사 호실

    private val washingRoom: String? = null //세탁구역
}
