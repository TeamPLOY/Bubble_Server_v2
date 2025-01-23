package com.ploy.bubble_server_v3.common.jwt.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}