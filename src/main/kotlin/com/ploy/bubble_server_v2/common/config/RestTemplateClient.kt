package com.ploy.bubble_server_v2.common.config

import org.springframework.boot.web.client.RestTemplateBuilder // RestTemplate을 빌드하는 데 사용되는 빌더 클래스
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class RestTemplateClient {
    @Bean
    open fun restTemplate(builder: RestTemplateBuilder): RestTemplate { // 메서드를 open으로 선언
        return builder.build()
    }
}