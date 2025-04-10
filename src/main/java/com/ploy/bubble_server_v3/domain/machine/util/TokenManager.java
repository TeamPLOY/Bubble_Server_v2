package com.ploy.bubble_server_v3.domain.machine.util;

import com.ploy.bubble_server_v3.domain.machine.domain.OAuthToken;
import com.ploy.bubble_server_v3.domain.machine.exception.AccessTokenRefreshException;
import com.ploy.bubble_server_v3.domain.machine.exception.AccessTokenRetrievalFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenManager {
    private static final int TOKEN_LIFETIME_SECONDS = 3600;

    private final OAuthTokenUtil oAuthTokenUtil;

    private String accessToken;
    private LocalDateTime tokenIssuedAt;

    public String getValidAccessToken() {
        ensureValidToken();
        return accessToken;
    }

    private void ensureValidToken() {
        if (accessToken != null && tokenIssuedAt != null) {
            LocalDateTime now = LocalDateTime.now();
            long elapsedSeconds = java.time.Duration.between(tokenIssuedAt, now).getSeconds();

            if (elapsedSeconds > TOKEN_LIFETIME_SECONDS) {
                refreshAccessToken();
            }
        } else {
            refreshAccessToken();
        }
    }

    private void refreshAccessToken() {
        try {
            OAuthToken token = oAuthTokenUtil.getAccessToken();
            if (token != null && token.accessToken() != null) {
                accessToken = token.accessToken();
                tokenIssuedAt = LocalDateTime.now();
            } else {
                throw new AccessTokenRetrievalFailedException();
            }
        } catch (Exception e) {
            throw new AccessTokenRefreshException();
        }
    }
}