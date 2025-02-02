package com.ploy.bubble_server_v3.domain.user.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WashingRoom {
    // 열거형은 학기마다 달라질 수 있습니다.
    A41("A41", "A", 401, 417, 0, 0),
    A42("A42", "A", 418, 434, 0, 0),
    A31("A31", "A", 302, 307, 327, 334),
    A32("A32", "A", 308, 326, 0, 0),

    B41("B41", "B", 401, 417, 0, 0),
    B42("B42", "B", 418, 434, 0, 0),
    B31("B31", "B", 302, 307, 327, 334),
    B32("B32", "B", 308, 326, 0, 0),

    UNKNOWN("UNKNOWN", "", 0, 0, 0, 0);

    private final String code;
    private final String prefix;
    private final int minRoom;
    private final int maxRoom;
    private final int minRoomExtra;
    private final int maxRoomExtra;

    public static WashingRoom findWashingRoom(String prefix, int roomNumber) {
        for (WashingRoom room : values()) {
            if (room.prefix.equals(prefix) &&
                    ((room.minRoom <= roomNumber && roomNumber <= room.maxRoom) ||
                            (room.minRoomExtra <= roomNumber && roomNumber <= room.maxRoomExtra))) {
                return room;
            }
        }
        return UNKNOWN;
    }
}
