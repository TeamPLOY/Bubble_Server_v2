package com.ploy.bubble_server_v2.common.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    var issuer: String? = null,
    var clientSecret: String? = null,
    var tokenExpire: Int = 0,
    var refreshTokenExpire: Int = 0
)