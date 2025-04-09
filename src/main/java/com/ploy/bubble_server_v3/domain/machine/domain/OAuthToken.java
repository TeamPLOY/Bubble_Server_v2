package com.ploy.bubble_server_v3.domain.machine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthToken(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("expires_in")
        Integer expiresIn,
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
