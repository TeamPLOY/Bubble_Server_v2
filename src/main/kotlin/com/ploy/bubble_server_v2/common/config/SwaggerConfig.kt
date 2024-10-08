package com.ploy.bubble_server_v2.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SwaggerConfig {

    @Bean
    open fun customOpenAPI(): OpenAPI {
        return OpenAPI().apply {
            info = Info().title("BUBBLE_V2")
            addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            components = Components().apply {
                addSecuritySchemes("bearerAuth", SecurityScheme().apply {
                    type = SecurityScheme.Type.HTTP
                    scheme = "bearer"
                    bearerFormat = "JWT"
                    `in`(SecurityScheme.In.HEADER)
                    name = "Authorization"
                })
            }
        }
    }
}
