package com.ploy.bubble_server_v3.domain.user.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WashingRoomTest {

    @Test
    @DisplayName("방 번호로 세탁실 찾기 - 존재하는 방")
    void findWashingRoom_validRoom() {
        assertEquals("A41", WashingRoom.findWashingRoom("A", 405));
        assertEquals("A42", WashingRoom.findWashingRoom("A", 420));
        assertEquals("A31", WashingRoom.findWashingRoom("A", 305));
        assertEquals("A31", WashingRoom.findWashingRoom("A", 330)); // Extra range
        assertEquals("A32", WashingRoom.findWashingRoom("A", 310));

        assertEquals("B41", WashingRoom.findWashingRoom("B", 410));
        assertEquals("B42", WashingRoom.findWashingRoom("B", 420));
        assertEquals("B31", WashingRoom.findWashingRoom("B", 303));
        assertEquals("B31", WashingRoom.findWashingRoom("B", 332)); // Extra range
        assertEquals("B32", WashingRoom.findWashingRoom("B", 315));
    }

    @Test
    @DisplayName("방 번호로 세탁실 찾기 - 존재하지 않는 방")
    void findWashingRoom_invalidRoom() {
        assertEquals("UNKNOWN", WashingRoom.findWashingRoom("A", 500)); // 존재하지 않는 방
        assertEquals("UNKNOWN", WashingRoom.findWashingRoom("B", 100)); // 존재하지 않는 방
        assertEquals("UNKNOWN", WashingRoom.findWashingRoom("C", 305)); // 존재하지 않는 prefix
        assertEquals("UNKNOWN", WashingRoom.findWashingRoom("", 305));  // 빈 prefix
    }
}
