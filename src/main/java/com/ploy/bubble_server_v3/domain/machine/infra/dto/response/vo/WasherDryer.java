package com.ploy.bubble_server_v3.domain.machine.infra.dto.response.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WasherDryer(
        @JsonProperty("remainTimeMinute")
        Integer remainTimeMinute
) {
}
