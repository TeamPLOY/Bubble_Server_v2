package com.ploy.bubble_server_v2.common.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.ploy.bubble_server_v2.common.dto.TokenResponse
import java.util.Date

class Jwt(
    clientSecret: String,
    private val issuer: String,
    private val tokenExpire: Int,
    private val refreshTokenExpire: Int
) {
    private val algorithm: Algorithm = Algorithm.HMAC512(clientSecret)
    private val jwtVerifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .build()

    // 액세스 토큰을 생성하는 함수
    fun generateAccessToken(claims: Claims): String {
        return sign(claims, tokenExpire)
    }

    // 리프레시 토큰을 생성하는 함수
    fun generateRefreshToken(claims: Claims): String {
        return sign(claims, refreshTokenExpire)
    }

    // 액세스 토큰과 리프레시 토큰을 모두 생성하는 함수
    fun generateAllToken(claims: Claims): TokenResponse {
        return TokenResponse(generateAccessToken(claims), generateRefreshToken(claims))
    }

    // 토큰 서명 함수
    private fun sign(claims: Claims, expireTime: Int): String {
        val now = Date() // 현재 시간
        return JWT.create()
            .withIssuer(issuer)
            .withIssuedAt(now)
            .withExpiresAt(Date(now.time + expireTime * 1000L))
            .withClaim("memberId", claims.memberId)
            .sign(algorithm)
    }

    // 토큰을 검증하는 함수
    fun verify(token: String): Claims {
        return Claims(jwtVerifier.verify(token))
    }

    // Claims 데이터 클래스
    data class Claims(
        val memberId: Long?,
        val iat: Date?,
        val exp: Date?
    ) {
        constructor(decodedJwt: DecodedJWT) : this(
            memberId = decodedJwt.getClaim("memberId").takeIf { !it.isNull }?.asLong(),
            iat = decodedJwt.issuedAt,
            exp = decodedJwt.expiresAt
        )

        companion object {
            // 주어진 memberId로 Claims 생성
            fun from(memberId: Long): Claims {
                return Claims(memberId, null, null)
            }
        }

        override fun toString(): String {
            return "Claims(memberId=$memberId, iat=$iat, exp=$exp)"
        }
    }
}
