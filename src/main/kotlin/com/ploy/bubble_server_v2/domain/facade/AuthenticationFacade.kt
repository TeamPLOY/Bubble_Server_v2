package com.ploy.bubble_server_v2.domain.facade


import com.ploy.bubble_server_v2.common.dto.TokenResponse
import com.ploy.bubble_server_v2.common.exception.BusinessException
import lombok.RequiredArgsConstructor

@org.springframework.stereotype.Component
@RequiredArgsConstructor
class AuthenticationFacade {
    private val authService: AuthService? = null // AuthService 의존성 주입
    private val userService: UserService? = null // UserService 의존성 주입

    @org.springframework.transaction.annotation.Transactional
    fun login(req: LoginRequest): LoginResponse {
        return authService.login(req.getEmail(), req.getPassword())
    }

    @org.springframework.transaction.annotation.Transactional // 이 메소드는 트랜잭션이 필요하다는 것을 나타냄
    fun logout(memberId: Long?) {
        // AuthService의 logout 메소드를 호출하여 로그아웃 처리 수행
        authService.logout(memberId)
    }

    @org.springframework.transaction.annotation.Transactional
    fun refreshToken(tokenRefreshRequest: TokenRefreshRequest?): TokenResponse {
        return authService.refreshToken(tokenRefreshRequest)
    }

    fun getUserInfo(memberId: Long?): UserResponse {
        return userService.getUserInfo(memberId)
    }

    @org.springframework.transaction.annotation.Transactional
    fun deleteUser(memberId: Long?) {
        userService.deleteUser(memberId)
    }

    @org.springframework.transaction.annotation.Transactional
    fun signUp(req: SignUpRequest?) {
        try {
            userService.create(req)
        } catch (e: java.lang.Exception) {
            throw BusinessException(ErrorCode.DUPLICATED)
        }
    }
}

