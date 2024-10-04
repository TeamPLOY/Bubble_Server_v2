package com.ploy.bubble_server_v2.common.exception

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.security.core.AuthenticationException

class CustomAuthenticationEntryPointTest {

    private lateinit var entryPoint: CustomAuthenticationEntryPoint
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse

    @BeforeEach
    fun setup() {
        entryPoint = CustomAuthenticationEntryPoint()
        request = mock(HttpServletRequest::class.java)
        response = mock(HttpServletResponse::class.java)
    }

    @Test
    fun `test commence when authentication fails`() {
        val authException = mock(AuthenticationException::class.java)

        `when`(request.requestURI).thenReturn("/login")
        `when`(authException.message).thenReturn("Authentication failed")

        entryPoint.commence(request, response, authException)

        verify(response).characterEncoding = "UTF-8"
        verify(response).contentType = "application/json"
        verify(response).status = HttpServletResponse.SC_UNAUTHORIZED

        val writer = response.writer
        verify(writer).write(any(String::class.java))
        verify(writer).flush()
    }
}
