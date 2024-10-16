package com.ploy.bubble_server_v2.domain.user.model.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import lombok.*

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class UserResponse {
    @Schema(description = "이름")
    private val name: String? = null

    @Schema(description = "학번")
    private val studentNum = 0

    @Schema(description = "이메일")
    private val email: String? = null

    @Schema(description = "기숙사 호실")
    private val roomNum: String? = null

    @Schema(description = "세탁실 위치")
    private val washingRoom: String? = null
}
