package com.ploy.bubble_server_v3.domain.machine.infra.feign;

import com.ploy.bubble_server_v3.domain.machine.domain.OAuthToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "oauthClient", url = "https://kr.lgeapi.com/oauth/1.0/oauth2/token")
public interface OAuthFeign {

    @PostMapping(consumes = "application/x-www-form-urlencoded", produces = "application/json")
    OAuthToken getAccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("refresh_token") String refreshToken,
            @RequestHeader("x-lge-app-os") String appOs,
            @RequestHeader("x-lge-appkey") String clientId,
            @RequestHeader("x-lge-oauth-signature") String signature,
            @RequestHeader("x-lge-oauth-date") String timestamp
    );
}
