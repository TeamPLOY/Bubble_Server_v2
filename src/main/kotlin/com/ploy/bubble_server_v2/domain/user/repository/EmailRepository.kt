package com.ploy.bubble_server_v2.domain.user.repository

import com.ploy.bubble_server_v2.domain.user.model.entity.Email

import org.springframework.data.jpa.repository.JpaRepository

interface EmailRepository : JpaRepository<Email?, Long?> {
    // 이메일과 코드로 가장 최근 날짜의 하나의 레코드만 조회
    fun findTop1ByEmailOrderByIdDesc(email: String?): java.util.Optional<Email?>?
}

