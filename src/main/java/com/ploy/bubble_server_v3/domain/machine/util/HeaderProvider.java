package com.ploy.bubble_server_v3.domain.machine.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HeaderProvider {

    private final TokenManager tokenManager;
    private final HeaderProperties headerProperties;

    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();

        String accessToken = tokenManager.getValidAccessToken();

        headers.put("x-country-code", headerProperties.getCountryCode());
        headers.put("x-service-phase", headerProperties.getServicePhase());
        headers.put("User-Agent", headerProperties.getUserAgent());
        headers.put("x-thinq-app-ver", headerProperties.getThinqAppVer());
        headers.put("x-thinq-app-type", headerProperties.getThinqAppType());
        headers.put("x-language-code", headerProperties.getLanguageCode());
        headers.put("x-thinq-app-logintype", headerProperties.getThinqAppLogintype());
        headers.put("x-os-version", headerProperties.getOsVersion());
        headers.put("x-client-id", headerProperties.getClientId());
        headers.put("x-thinq-app-level", headerProperties.getThinqAppLevel());
        headers.put("x-app-version", headerProperties.getAppVersion());
        headers.put("x-user-no", headerProperties.getUserNo());
        headers.put("x-service-code", headerProperties.getServiceCode());
        headers.put("Accept-Language", headerProperties.getAcceptLanguage());
        headers.put("x-message-id", UUID.randomUUID().toString());
        headers.put("x-emp-token", accessToken);
        headers.put("x-origin", headerProperties.getOrigin());
        headers.put("Accept", "application/json");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("x-model-name", headerProperties.getModelName());
        headers.put("Content-Type", headerProperties.getContentType());
        headers.put("x-api-key", headerProperties.getApiKey());
        headers.put("x-thinq-app-os", headerProperties.getThinqAppOs());

        return headers;
    }
}
