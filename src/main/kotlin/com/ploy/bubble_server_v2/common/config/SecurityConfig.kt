package com.ploy.bubble_server_v2.common.config

import com.ploy.bubble_server_v2.common.exception.CustomAccessDeniedHandler
import com.ploy.bubble_server_v2.common.exception.CustomAuthenticationEntryPoint
import com.ploy.bubble_server_v2.common.jwt.Jwt
import com.ploy.bubble_server_v2.common.jwt.JwtAuthenticationFilter
import com.ploy.bubble_server_v2.common.jwt.JwtProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
open class SecurityConfig(private val jwtProperties: JwtProperties) {

    @Bean
    open fun jwt(): Jwt {
        return Jwt(
            jwtProperties.clientSecret ?: "",  // null일 경우 빈 문자열로 대체
            jwtProperties.issuer ?: "",         // null일 경우 빈 문자열로 대체
            jwtProperties.tokenExpire,
            jwtProperties.refreshTokenExpire
        )
    }

    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE")
            allowedHeaders = listOf(
                "Origin", "Accept", "X-Requested-With", "Content-Type",
                "Access-Control-Request-Method", "Access-Control-Request-Headers",
                "Authorization", "access_token", "refresh_token"
            )
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    open fun accessDeniedHandler(): AccessDeniedHandler {
        return CustomAccessDeniedHandler()
    }

    @Bean
    open fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return CustomAuthenticationEntryPoint()
    }

    @Bean
    open fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(jwt())
    }

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/**").permitAll()
                    .anyRequest().authenticated()
            }
            .headers { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .rememberMe { it.disable() }
            .logout { it.disable() }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(accessDeniedHandler())
            }
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .cors { it.configurationSource(corsConfigurationSource()) } // CORS 설정을 명시적으로 추가

        return http.build()
    }
}
