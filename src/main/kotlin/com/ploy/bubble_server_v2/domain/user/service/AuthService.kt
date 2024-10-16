package com.ploy.bubble_server_v2.domain.user.service

import com.ploy.bubble_server_v2.common.exception.BusinessException
import com.ploy.bubble_server_v2.common.exception.ErrorCode
import com.ploy.bubble_server_v2.common.jwt.Jwt
import com.ploy.bubble_server_v2.domain.user.model.dto.request.TokenRefreshRequest
import com.ploy.bubble_server_v2.domain.user.model.dto.response.LoginResponse
import com.ploy.bubble_server_v2.domain.user.model.dto.response.TokenResponse
import com.ploy.bubble_server_v2.domain.user.model.entity.User
import com.ploy.bubble_server_v2.domain.user.repository.UserRepository

import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
class AuthService {
    private val userRepository: UserRepository? = null
    private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

    private val jwt: Jwt? = null

    fun login(email: String?, password: String?): LoginResponse {
        try {
            // 이메일로 사용자 찾기
            val user: User = userRepository.findByEmail(email)
                .orElseThrow { BusinessException(ErrorCode.EMAIL_NOT_FOUND) } // 이메일이 존재하지 않을 때

            // 비밀번호 일치 여부 확인
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw BusinessException(ErrorCode.INVALID_PASSWORD) // 비밀번호가 틀렸을 때
            }

            // 비밀번호가 일치하면 로그인 응답 객체를 생성하여 반환합니다.
            return getLoginResponse(user)
        } catch (e: BusinessException) {
            AuthService.log.error("로그인 실패: {}", e.getMessage())
            throw e
        } catch (e: java.lang.Exception) {
            AuthService.log.error("로그인 중 알 수 없는 오류 발생: {}", e.message)
            throw BusinessException(ErrorCode.UNKNOWN_ERROR)
        }
    }

    @org.springframework.transaction.annotation.Transactional
    fun logout(userId: Long?) {
        val user: Unit = userRepository.findById(userId)
            .orElseThrow { BusinessException(ErrorCode.ENTITY_NOT_FOUND) }
        user.setRefreshToken("") // setRefreshToken 메서드 호출
    }

    fun getLoginResponse(user: User): LoginResponse {
        val tokens: TokenResponse = publishToken(user)
        return if (user.getEmail() == null || user.getEmail().isEmpty()) {
            LoginResponse(tokens) //isNewUser은 떄떄로 필요함
        } else {
            LoginResponse(tokens)
        }
    }

    @org.springframework.transaction.annotation.Transactional
    fun publishToken(user: User): TokenResponse {
        val tokenResponse: TokenResponse = jwt.generateAllToken(
            Jwt.Claims.from(
                user.getId()
            )
        )

        user.setRefreshToken(tokenResponse.refreshToken())

        return tokenResponse
    }

    @org.springframework.transaction.annotation.Transactional
    fun refreshToken(tokenRefreshRequest: TokenRefreshRequest): TokenResponse {
        val user: Unit = userRepository.findByRefreshToken(tokenRefreshRequest.refreshToken())
        if (user.isPresent() === false) {
            throw org.springframework.security.access.AccessDeniedException("refresh token 이 만료되었습니다.")
        }

        val memberId: Long

        try {
            val claims: Claims = jwt.verify(user.get().getRefreshToken())
            memberId = claims.getMemberId()
        } catch (e: java.lang.Exception) {
            AuthService.log.warn("Jwt 처리중 문제가 발생하였습니다 : {}", e.message)
            throw e
        }
        val tokenResponse: TokenResponse = jwt.generateAllToken(Jwt.Claims.from(memberId))

        user.get().setRefreshToken(tokenResponse.refreshToken())

        return tokenResponse
    }
}

