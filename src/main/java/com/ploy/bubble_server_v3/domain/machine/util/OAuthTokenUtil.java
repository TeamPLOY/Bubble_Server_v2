package com.ploy.bubble_server_v3.domain.machine.util;

import com.ploy.bubble_server_v3.domain.machine.domain.OAuthToken;
import com.ploy.bubble_server_v3.domain.machine.infra.feign.OAuthFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthTokenUtil {

    @Value("${oauth.secret.key}")
    private String oauthSecretKey;

    @Value("${oauth.refresh.token}")
    private String refreshToken;

    @Value("${oauth.client.id}")
    private String clientId;

    private final OAuthSignatureUtil oAuthSignatureUtil;
    private final OAuthFeign oAuthFeignClient;

    public OAuthToken getAccessToken() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter rfc5322Formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        String timestamp = now.format(rfc5322Formatter);

        String query = "grant_type=refresh_token&refresh_token=" + URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);
        String requestUrl = "/oauth/1.0/oauth2/token?" + query;

        String signature = oAuthSignatureUtil.getSignature(requestUrl + "\n" + timestamp, oauthSecretKey);

        return oAuthFeignClient.getAccessToken(
                "refresh_token",
                refreshToken,
                "ADR",
                clientId,
                signature,
                timestamp
        );
    }
}
