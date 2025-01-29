package com.ploy.bubble_server_v3.domain.user.domain;

import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsersTest {

    @Test
    void updatePassword_shouldUpdatePassword() {
        // given
        Users user = Users.builder()
                .id(1L)
                .password("oldPassword")
                .name("한태영")
                .stuNum(1234)
                .email("test@example.com")
                .roomNum("B304")
                .washingRoom(WashingRoom.B31)  // WashingRoom은 실제로 어떤 값인지 확인해야 합니다.
                .role(Role.USER)  // Role도 실제 정의된 값으로 설정
                .build();

        String newPassword = "newPassword123";

        // when
        user.updatePassword(newPassword);

        // then
        assertEquals(newPassword, user.getPassword(), "Password should be updated");
    }
}
