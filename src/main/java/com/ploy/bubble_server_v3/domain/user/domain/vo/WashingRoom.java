package com.ploy.bubble_server_v3.domain.user.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WashingRoom {
    //호실에 대한 열거형은 학기마다 변동사항이 있을 수 있습니다.
    B41("B41", "B", 401, 417, 0, 0),
    B42("B42", "B", 418, 434, 0, 0),
    B31("B31", "B", 302, 307, 327, 334),
    B32("B32", "B", 308, 326, 0, 0),
    UNKNOWN("UNKNOWN", "", 0, 0, 0, 0);

    private final String washingRoom;
    private final String prefix;
    private final int minRoom;
    private final int maxRoom;
    private final int minRoomExtra;
    private final int maxRoomExtra;

    public static String findWashingRoom(String prefix, int roomNumber) {
        for (WashingRoom room : values()) {
            if (room.prefix.equals(prefix) &&
                    ((room.minRoom <= roomNumber && room.maxRoom >= roomNumber) ||
                            (room.minRoomExtra <= roomNumber && room.maxRoomExtra >= roomNumber))) {
                return room.washingRoom;
            }
        }
        return UNKNOWN.washingRoom;
    }
}
