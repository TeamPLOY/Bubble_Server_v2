package com.ploy.bubble_server_v3.domain.machine.presentation;

import com.ploy.bubble_server_v3.domain.machine.domain.WashingMachine;
import com.ploy.bubble_server_v3.domain.machine.service.WashingMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WashingMachineController {
    private final WashingMachineService washingMachineService;

    @GetMapping("/machines")
    public List<WashingMachine> getWashingMachines() {
        return washingMachineService.getAllWashingMachinesFromRedis();
    }
}
