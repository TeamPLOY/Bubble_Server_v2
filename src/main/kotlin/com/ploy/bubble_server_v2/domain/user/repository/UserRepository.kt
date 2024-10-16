package com.ploy.bubble_server_v2.domain.user.repository

import com.ploy.bubble_server_v2.domain.user.model.entity.User

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserRepository : JpaRepository<User?, Long?>, JpaSpecificationExecutor<User?> {
    fun findByRefreshToken(refreshToken: String?): java.util.Optional<User?>?

    fun findByEmail(email: String?): java.util.Optional<User?>?

    fun existsByEmail(email: String?): Boolean
}

