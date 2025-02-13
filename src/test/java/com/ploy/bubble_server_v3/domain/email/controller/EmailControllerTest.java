package com.ploy.bubble_server_v3.domain.email.controller;

import com.ploy.bubble_server_v3.domain.email.presentation.EmailController;
import com.ploy.bubble_server_v3.domain.email.presentation.dto.request.EmailCertificationRequest;
import com.ploy.bubble_server_v3.domain.email.presentation.dto.request.EmailSendRequest;
import com.ploy.bubble_server_v3.domain.email.service.CommandEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmailControllerTest {

    @Mock
    private CommandEmailService commandEmailService;

    @InjectMocks
    private EmailController emailController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }

    @Test
    @DisplayName("이메일 전송 성공")
    void testSendEmail() throws Exception {
        EmailSendRequest emailSendRequest = new EmailSendRequest("test@example.com");
        doNothing().when(commandEmailService).sendEmail(emailSendRequest);

        mockMvc.perform(post("/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\"}"))
                .andExpect(status().isNoContent());

        verify(commandEmailService, times(1)).sendEmail(any(EmailSendRequest.class));
    }

    @Test
    @DisplayName("이메일 인증 성공")
    void testCertificationEmail() throws Exception {
        EmailCertificationRequest emailCertificationRequest = new EmailCertificationRequest("123456", "test@example.com");
        when(commandEmailService.certificationEmail(emailCertificationRequest)).thenReturn(true);

        mockMvc.perform(post("/email/certification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\": \"123456\", \"email\": \"test@example.com\"}"))
                .andExpect(status().isOk());

        verify(commandEmailService, times(1)).certificationEmail(any(EmailCertificationRequest.class));
    }
}