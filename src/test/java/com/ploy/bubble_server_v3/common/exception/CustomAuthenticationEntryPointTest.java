package com.ploy.bubble_server_v3.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomAuthenticationEntryPointTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;
    private CustomAuthenticationEntryPoint entryPoint;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // given
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = mock(PrintWriter.class);

        entryPoint = new CustomAuthenticationEntryPoint();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void commence_인증_되지_않은_사용자_응답_반환() throws IOException {
        // given
        when(response.getWriter()).thenReturn(writer);

        // when
        entryPoint.commence(request, response, null);

        // then
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ArgumentCaptor<String> responseBodyCaptor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(responseBodyCaptor.capture());

        String responseBody = responseBodyCaptor.getValue();
        assertNotNull(responseBody);

        assertTrue(responseBody.contains("AUTHENTICATION_FAILED"));
        assertTrue(responseBody.contains("사용자 인증에 실패하였습니다."));
    }
}
