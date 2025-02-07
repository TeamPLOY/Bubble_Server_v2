package com.ploy.bubble_server_v3.domain.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdatePasswordRequest;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdateRoomNumRequest;
import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdateStuNumRequest;
import com.ploy.bubble_server_v3.domain.user.service.CommandUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@WithMockUser(roles = "USER")
class UserControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CommandUserService commandUserService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("비밀번호 변경 요청 성공")
    void updatePassword_success() throws Exception {
        UpdatePasswordRequest request = new UpdatePasswordRequest("currentPassword123","newPassword123");
        lenient().doNothing().when(commandUserService).updatePassword(any(), any());

        mockMvc.perform(patch("/user/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("학번 변경 요청 성공")
    void updateStuNum_success() throws Exception {
        UpdateStuNumRequest request = new UpdateStuNumRequest(2116);

        lenient().doNothing().when(commandUserService).updateStuNum(any(), any());

        mockMvc.perform(patch("/user/stuNum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("기숙사 호실 변경 요청 성공")
    void updateRoomNum_success() throws Exception {
        UpdateRoomNumRequest request = new UpdateRoomNumRequest("B304");

        lenient().doNothing().when(commandUserService).updateRoomNum(any(), any());

        mockMvc.perform(patch("/user/roomNum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }
}
