package com.ploy.bubble_server_v3.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

class CustomAccessDeniedHandlerTest {

    private CustomAccessDeniedHandler customAccessDeniedHandler;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        customAccessDeniedHandler = new CustomAccessDeniedHandler();
    }

    @Test
    void handle_접근_거부_처리_테스트() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(request.getRequestURI()).thenReturn("/test-url");
        when(response.getWriter()).thenReturn(writer);

        customAccessDeniedHandler.handle(request, response, new AccessDeniedException("Access Denied"));

        verify(response).setCharacterEncoding("UTF-8");
        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);

        String expectedResponse = objectMapper.writeValueAsString(
                ErrorResponse.of(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "접근 권한이 없습니다.", "/test-url")
        );

        verify(writer).write(expectedResponse);
    }
}
