package com.ploy.bubble_server_v2.common.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestTemplate

class RestTemplateClientTest {

    private val restTemplateClient = RestTemplateClient()

    @Test
    fun `restTemplate should be created`() {
        val restTemplateBuilder = Mockito.mock(RestTemplateBuilder::class.java)
        val restTemplate = RestTemplate()

        `when`(restTemplateBuilder.build()).thenReturn(restTemplate)

        val result = restTemplateClient.restTemplate(restTemplateBuilder)

        assertNotNull(result) // RestTemplate 객체가 null이 아님을 확인
        assert(result is RestTemplate) // 결과가 RestTemplate 타입임을 확인
    }
}
