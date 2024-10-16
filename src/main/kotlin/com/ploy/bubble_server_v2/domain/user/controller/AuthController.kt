package com.ploy.bubble_server_v2.domain.user.controller

import com.ploy.bubble_server_v2.domain.facade.AuthenticationFacade
import com.ploy.bubble_server_v2.common.util.AuthenticationUtil.getMemberId

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증/인가")
@Slf4j
@RestController
@RequiredArgsConstructor
class AuthController {
    private val authenticationFacade: AuthenticationFacade? = null

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    fun signUpParent(
        @RequestBody req: @Valid SignUpRequest?
    ): ResponseEntity<String> {
        authenticationFacade.signUp(req)
        return ResponseEntity<String>("회원가입이 완료되었습니다.", HttpStatus.OK)
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest?): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok<T>(authenticationFacade.login(req))
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    fun logout(): ResponseEntity<Void> {
        authenticationFacade.logout(getMemberId())
        return ResponseEntity.noContent().build<Void>()
    }

    @Operation(summary = "토큰 갱신")
    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestBody tokenRefreshRequest: @Valid TokenRefreshRequest?
    ): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok<T>(authenticationFacade.refreshToken(tokenRefreshRequest))
    }

    @get:GetMapping("/user")
    @get:Operation(summary = "사용자 정보 확인")
    val userInfo: ResponseEntity<UserResponse>
        get() {
            val userInfo: UserResponse = authenticationFacade.getUserInfo(getMemberId())
            return ResponseEntity.ok<UserResponse>(userInfo)
        }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/user")
    fun deleteUser(): ResponseEntity<Void> {
        authenticationFacade.deleteUser(getMemberId())
        return ResponseEntity.noContent().build<Void>()
    }
}