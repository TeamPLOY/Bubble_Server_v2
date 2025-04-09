package com.ploy.bubble_server_v3.domain.machine.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "header")
public class HeaderProperties {
    private String countryCode;
    private String servicePhase;
    private String userAgent;
    private String thinqAppVer;
    private String thinqAppType;
    private String languageCode;
    private String thinqAppLogintype;
    private String osVersion;
    private String clientId;
    private String thinqAppLevel;
    private String appVersion;
    private String userNo;
    private String serviceCode;
    private String acceptLanguage;
    private String origin;
    private String modelName;
    private String contentType;
    private String apiKey;
    private String thinqAppOs;
}
