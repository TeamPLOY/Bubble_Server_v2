package com.ploy.bubble_server_v2.common.util


import com.ploy.bubble_server_v2.common.filter.CustomAuthenticationFilter
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

/**
 * 인증 관련 유틸리티 클래스입니다.
 */
object AuthenticationUtil {

    private val log = LoggerFactory.getLogger(CustomAuthenticationFilter::class.java) // 로그 사용

    /**
     * 인증된 사용자의 회원 ID를 가져옵니다.
     * @return 인증된 사용자의 회원 ID. 인증되지 않은 경우 null을 반환합니다.
     */
    fun getMemberId(): Long? {
        val anonymous = isAnonymous().toString()
        log.warn("known : $anonymous")
        return if (isAnonymous()) {
            null
        } else {
            getAuthentication()?.principal as? Long
        }
    }

    /**
     * 사용자가 익명 사용자(인증되지 않은 사용자)인지 확인합니다.
     *
     * @return 익명 사용자이면 true, 그렇지 않으면 false를 반환합니다.
     */
    fun isAnonymous(): Boolean {
        val authentication = getAuthentication()
        return authentication == null || authentication.principal == "anonymousUser"
    }

    /**
     * 현재 인증 객체를 가져옵니다.
     *
     * @return 현재 인증 객체
     */
    private fun getAuthentication(): Authentication? {
        val context = SecurityContextHolder.getContext()
        log.info("Context : $context")
        val result = context.authentication
        log.info("result : $result")
        return result
    }
}