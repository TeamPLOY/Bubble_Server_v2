package com.ploy.bubble_server_v2.domain.user.model.dto.vo

import lombok.Getter
import lombok.RequiredArgsConstructor

data class RoomRange(val start: Int, val end: Int)

@Getter
@RequiredArgsConstructor
class WashingRoom private constructor(
    val name: String,
    val prefix: String,
    val roomRanges: List<RoomRange>
) {
    companion object {
        private val rooms = listOf(
            WashingRoom("B41", "B", listOf(RoomRange(401, 417))),
            WashingRoom("B42", "B", listOf(RoomRange(418, 434))),
            WashingRoom("B31", "B", listOf(RoomRange(302, 307), RoomRange(327, 334))),
            WashingRoom("B32", "B", listOf(RoomRange(308, 326)))
        )

        val UNKNOWN = WashingRoom("UNKNOWN", "", emptyList())

        fun findWashingRoom(prefix: String, roomNumber: Int): WashingRoom {
            return rooms.find { room ->
                room.prefix == prefix && room.roomRanges.any { range ->
                    roomNumber in range.start..range.end
                }
            } ?: UNKNOWN
        }

        fun values(): List<WashingRoom> = rooms + UNKNOWN
    }
}