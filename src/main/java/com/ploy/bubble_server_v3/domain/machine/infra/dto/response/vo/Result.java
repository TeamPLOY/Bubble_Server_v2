package com.ploy.bubble_server_v3.domain.machine.infra.dto.response.vo;

import java.util.List;

public record Result(
        List<Device> devices
) {
}
