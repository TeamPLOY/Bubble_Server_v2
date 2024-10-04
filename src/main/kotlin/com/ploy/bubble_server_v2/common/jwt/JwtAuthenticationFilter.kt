package com.ploy.bubble_server_v2.common.jwt

import com.ploy.bubble_server_v2.common.filter.CustomAuthenticationFilter
import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@RequiredArgsConstructor
class JwtAuthenticationFilter(
    private val jwt: Jwt
) : GenericFilter() {

    private val log = LoggerFactory.getLogger(CustomAuthenticationFilter::class.java) // 로그 사용

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletRequest = request as HttpServletRequest
        val httpServletResponse = response as HttpServletResponse

        if (SecurityContextHolder.getContext().authentication == null) {
            log.info("getAuthentication is null")
            val token = getAccessToken(httpServletRequest)

            log.info("토큰 : $token")

            token?.let {
                try {
                    val claims = verify(it)
                    val memberId = claims.memberId

                    log.info("memberId : $memberId")

                    memberId?.let { id ->
                        val authentication = UsernamePasswordAuthenticationToken(id, null, null)
                        log.info("authentication : $authentication")
                        SecurityContextHolder.getContext().authentication = authentication
                    }
                } catch (e: Exception) {
                    log.warn("Jwt 처리중 문제가 발생하였습니다 : {}", e.message)
                }
            }
        } else {
            log.debug("이미 인증 객체가 존재합니다 : {}",
                SecurityContextHolder.getContext().authentication)
        }
        chain.doFilter(httpServletRequest, httpServletResponse)
    }

    private fun getAccessToken(request: HttpServletRequest): String? {
        log.warn("리퀘스트 로그 시작")

        log.warn("Method: ${request.method}")
        log.warn("Request URI: ${request.requestURI}")
        log.warn("Query String: ${request.queryString}")

        log.warn("Headers:")
        val headerNames = request.headerNames
        while (headerNames.hasMoreElements()) {
            val headerName = headerNames.nextElement()
            log.info("헤더: $headerName 값: ${request.getHeader(headerName)}")
        }

        val authorizationHeader = request.getHeader("Authorization") // Authorization 헤더에서 토큰을 가져옴
        log.info("Raw Authorization Header : $authorizationHeader")

        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            log.info("Authorization 헤더에 Bearer 토큰 존재")
            val token = authorizationHeader.substring(7) // 'Bearer ' 이후의 토큰만 추출
            try {
                URLDecoder.decode(token, StandardCharsets.UTF_8).also {
                    log.info("Decoded accessToken : $it")
                }
            } catch (e: Exception) {
                log.error("엑세스 토큰 디코딩 실패: ${e.message}", e)
                null
            }
        } else {
            log.warn("Authorization 헤더가 null이거나 Bearer 토큰이 없습니다.")
            null
        }
    }

    private fun verify(token: String): Jwt.Claims {
        return jwt.verify(token)
    }
}