package com.ploy.bubble_server_v3.domain.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ploy.bubble_server_v3.common.jwt.dto.TokenResponse;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.LoginRequest;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.QuitRequest;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.SignUpRequest;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.TokenRefreshRequest;
import com.ploy.bubble_server_v3.domain.auth.service.CommandAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WithMockUser(roles = "USER")
class AuthControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CommandAuthService commandAuthService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("회원가입 요청 성공")
    void signUp_success() throws Exception {
        SignUpRequest request = new SignUpRequest("test@example.com", "password123!", "TestUser", 2116, "B314");

        lenient().doNothing().when(commandAuthService).signUp(any());

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("로그인 요청 성공")
    void login_success() throws Exception {
        LoginRequest request = new LoginRequest("user@example.com", "password123!", "deviceToken123");

        TokenResponse mockToken = new TokenResponse("access-token-value", "refresh-token-value");

        lenient().when(commandAuthService.login(any())).thenReturn(mockToken);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그아웃 요청 성공")
    void logout_success() throws Exception {
        TokenRefreshRequest request = new TokenRefreshRequest("refresh-token-value");

        lenient().doNothing().when(commandAuthService).logout(any());

        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("회원탈퇴 요청 성공")
    void quit_success() throws Exception {
        QuitRequest request = new QuitRequest("password123!");

        lenient().doNothing().when(commandAuthService).quit(any(), any());

        mockMvc.perform(delete("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("토큰 갱신 요청 성공")
    void refresh_success() throws Exception {
        TokenRefreshRequest request = new TokenRefreshRequest("refresh-token-value");

        TokenResponse mockToken = new TokenResponse("new-access-token", "new-refresh-token");

        lenient().when(commandAuthService.refresh(any())).thenReturn(mockToken);

        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
