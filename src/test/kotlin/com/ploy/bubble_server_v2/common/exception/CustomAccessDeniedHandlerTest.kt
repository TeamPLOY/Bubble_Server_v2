package com.ploy.bubble_server_v2.common.exception

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.security.access.AccessDeniedException
import java.io.PrintWriter

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

        // PrintWriter 객체를 mock하여 response.writer 호출 시 반환되도록 설정
        val writer = mock(PrintWriter::class.java)
        `when`(response.writer).thenReturn(writer)

        handler.handle(request, response, accessDeniedException)

        verify(response).characterEncoding = "UTF-8"
        verify(response).contentType = "application/json"
        verify(response).status = HttpServletResponse.SC_FORBIDDEN

        // writer 객체에 대해 write와 flush, close가 호출되는지 검증
        verify(writer).write(any(String::class.java))
        verify(writer).flush()
        verify(writer).close()
    }
}
