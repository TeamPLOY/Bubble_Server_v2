package com.ploy.bubble_server_v3.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.ploy.bubble_server_v3")
public class OpenFeignConfig {
}
