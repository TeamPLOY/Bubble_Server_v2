package com.ploy.bubble_server_v2.common.config

import com.ploy.bubble_server_v2.common.jwt.JwtProperties
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    lateinit var securityConfig: SecurityConfig

    @Autowired
    lateinit var jwtProperties: JwtProperties

    @Test
    fun `jwt bean should be created`() {
        val jwt = securityConfig.jwt()
        assertNotNull(jwt) // Jwt 객체가 null이 아님을 확인
        // 추가적으로 jwt의 속성 검증 가능
    }

    @Test
    fun `corsConfigurationSource should be created`() {
        val corsConfig = securityConfig.corsConfigurationSource()
        assertNotNull(corsConfig) // CORS 설정이 null이 아님을 확인
    }

    @Test
    fun `accessDeniedHandler should be created`() {
        val handler = securityConfig.accessDeniedHandler()
        assertNotNull(handler) // AccessDeniedHandler 객체가 null이 아님을 확인
    }

    @Test
    fun `authenticationEntryPoint should be created`() {
        val entryPoint = securityConfig.authenticationEntryPoint()
        assertNotNull(entryPoint) // AuthenticationEntryPoint 객체가 null이 아님을 확인
    }
}
