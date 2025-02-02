package com.ploy.bubble_server_v3.domain.user.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WashingRoomTest {

    @Test
    @DisplayName("방 번호로 세탁실 찾기 - 존재하는 방")
    void findWashingRoom_validRoom() {
        assertEquals(WashingRoom.A41, WashingRoom.findWashingRoom("A", 405));
        assertEquals(WashingRoom.A42, WashingRoom.findWashingRoom("A", 420));
        assertEquals(WashingRoom.A31, WashingRoom.findWashingRoom("A", 305));
        assertEquals(WashingRoom.A31, WashingRoom.findWashingRoom("A", 330));
        assertEquals(WashingRoom.A32, WashingRoom.findWashingRoom("A", 310));

        assertEquals(WashingRoom.B41, WashingRoom.findWashingRoom("B", 410));
        assertEquals(WashingRoom.B42, WashingRoom.findWashingRoom("B", 420));
        assertEquals(WashingRoom.B31, WashingRoom.findWashingRoom("B", 303));
        assertEquals(WashingRoom.B31, WashingRoom.findWashingRoom("B", 332));
        assertEquals(WashingRoom.B32, WashingRoom.findWashingRoom("B", 315));
    }

    @Test
    @DisplayName("방 번호로 세탁실 찾기 - 존재하지 않는 방")
    void findWashingRoom_invalidRoom() {
        assertEquals(WashingRoom.UNKNOWN, WashingRoom.findWashingRoom("A", 500));
        assertEquals(WashingRoom.UNKNOWN, WashingRoom.findWashingRoom("B", 100));
        assertEquals(WashingRoom.UNKNOWN, WashingRoom.findWashingRoom("C", 305));
        assertEquals(WashingRoom.UNKNOWN, WashingRoom.findWashingRoom("", 305));
    }
}
