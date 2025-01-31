package com.ploy.bubble_server_v3.domain.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateRoomNumRequest(
        @Schema(description = "변경할 기숙사 호실")
        String roomNum
) {
}
