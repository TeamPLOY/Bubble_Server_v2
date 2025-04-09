package com.ploy.bubble_server_v3.domain.machine.infra.dto.response.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Snapshot(
        @JsonProperty("washerDryer")
        WasherDryer washerDryer
) {
}
