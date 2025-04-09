package com.ploy.bubble_server_v3.domain.machine.infra.dto.response.vo;

public record Device(
        String alias,
        Snapshot snapshot
) {
}
