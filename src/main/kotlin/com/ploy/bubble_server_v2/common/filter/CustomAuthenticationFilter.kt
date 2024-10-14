package com.ploy.bubble_server_v2.common.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.GenericFilter
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.slf4j.LoggerFactory

class CustomAuthenticationFilter : GenericFilter() {

    companion object {
        private val TOKEN_USER_MAP = mutableMapOf<String, UserInfo>() // 토큰과 사용자 정보를 매핑하는 맵
        private val log = LoggerFactory.getLogger(CustomAuthenticationFilter::class.java) // 로그 사용
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletRequest = request as HttpServletRequest // HTTP 요청 객체로 변환
        val httpServletResponse = response as HttpServletResponse // HTTP 응답 객체로 변환

        if (SecurityContextHolder.getContext().authentication == null) { // 현재 인증 객체가 없을 때
            val token = getAccessToken(httpServletRequest) // 요청에서 액세스 토큰을 가져옴
            token?.let {
                try {
                    val userInfo = TOKEN_USER_MAP[token] // 토큰에 매핑된 사용자 정보를 가져옴
                    userInfo?.let { user ->
                        val memberId = user.memberId // 사용자 ID
                        val authorities = getAuthorities(user) // 사용자 권한 목록

                        if (memberId != null && authorities.isNotEmpty()) { // 사용자 ID와 권한이 존재하는 경우
                            val authentication = UsernamePasswordAuthenticationToken(memberId, null, authorities)
                            SecurityContextHolder.getContext().authentication = authentication // SecurityContext에 인증 객체 설정
                        }
                    }
                } catch (e: Exception) {
                    log.warn("인증 처리 중 문제가 발생했습니다: {}", e.message) // 경고 로그 출력
                }
            }
        } else {
            log.debug("이미 인증 객체가 존재합니다: {}", SecurityContextHolder.getContext().authentication) // 디버그 로그 출력
        }

        chain.doFilter(httpServletRequest, httpServletResponse) // 다음 필터로 요청과 응답 전달
    }

    private fun getAccessToken(request: HttpServletRequest): String? {
        return request.getHeader("access_token") // 요청 헤더에서 액세스 토큰을 가져옴
    }

    private fun getAuthorities(userInfo: UserInfo): List<GrantedAuthority> {
        return userInfo.roles.map { SimpleGrantedAuthority(it) } // 역할 목록을 권한 객체로 변환
    }

    data class UserInfo(
        val memberId: Long?, // 사용자 ID
        val roles: Array<String>, // 역할 배열
        val provider: String // 인증 제공자
    )
}