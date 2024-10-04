package com.ploy.bubble_server_v2.common.exception

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.security.access.AccessDeniedException

class CustomAccessDeniedHandlerTest {

    private lateinit var handler: CustomAccessDeniedHandler
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse

    @BeforeEach
    fun setup() {
        handler = CustomAccessDeniedHandler()
        request = mock(HttpServletRequest::class.java)
        response = mock(HttpServletResponse::class.java)
    }

    @Test
    fun `test handle when access is denied`() {
        val accessDeniedException = AccessDeniedException("Access Denied")

        `when`(request.requestURI).thenReturn("/test-url")

        handler.handle(request, response, accessDeniedException)

        verify(response).characterEncoding = "UTF-8"
        verify(response).contentType = "application/json"
        verify(response).status = HttpServletResponse.SC_FORBIDDEN

        val writer = response.writer
        verify(writer).write(any(String::class.java))
        verify(writer).flush()
        verify(writer).close()
    }
}
