package com.ploy.bubble_server_v3.domain.machine.infra.feign;

import com.ploy.bubble_server_v3.domain.machine.infra.dto.response.WashingMachineResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "washingMachineClient", url = "${washing.machine.api.url}")
public interface WashingMachineFeign {

    @GetMapping
    WashingMachineResponse getWashingMachineData(@RequestHeader Map<String, String> headers);
}