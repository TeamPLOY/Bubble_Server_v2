package com.ploy.bubble_server_v2.common.config

import io.swagger.v3.oas.models.OpenAPI
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SwaggerConfigTest {

    @Autowired
    lateinit var swaggerConfig: SwaggerConfig

    @Test
    fun `customOpenAPI should be created`() {
        val openAPI: OpenAPI = swaggerConfig.customOpenAPI()
        assertNotNull(openAPI) // OpenAPI 객체가 null이 아님을 확인
    }
}
