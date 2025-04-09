package com.ploy.bubble_server_v3.domain.machine.service;

import com.ploy.bubble_server_v3.domain.machine.domain.WashingMachine;
import com.ploy.bubble_server_v3.domain.machine.exception.WashingMachineDataFetchException;
import com.ploy.bubble_server_v3.domain.machine.infra.dto.response.WashingMachineResponse;
import com.ploy.bubble_server_v3.domain.machine.infra.feign.WashingMachineFeign;
import com.ploy.bubble_server_v3.domain.machine.util.DeviceProcessor;
import com.ploy.bubble_server_v3.domain.machine.util.HeaderProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WashingMachineService {

    private final WashingMachineFeign washingMachineFeign;
    private final HeaderProvider headerProvider;
    private final DeviceProcessor deviceProcessor;

    private static final List<String> SORT_ORDER = Arrays.asList(
            "세탁기1", "세탁기2", "세탁기3", "세탁기4", "건조기1", "건조기2"
    );

    public List<WashingMachine> getAllWashingMachines() {
        WashingMachineResponse response = fetchWashingMachineData();

        List<WashingMachine> result = new ArrayList<>();

        if (response != null && response.result() != null && response.result().devices() != null) {
            response.result().devices().forEach(device ->
                    deviceProcessor.processAllDevices(device, result));
        }

        return result;
    }

    public List<WashingMachine> getWashingMachinesByRoom(String roomId) {
        WashingMachineResponse response = fetchWashingMachineData();

        List<WashingMachine> result = new ArrayList<>();

        if (response != null && response.result() != null && response.result().devices() != null) {
            List<WashingMachine> finalResult = result;
            response.result().devices().forEach(device ->
                    deviceProcessor.processDeviceByRoom(device, finalResult, roomId));
        }

        result = sortMachines(result);

        return result;
    }

    private WashingMachineResponse fetchWashingMachineData() {
        try {
            return washingMachineFeign.getWashingMachineData(headerProvider.getRequestHeaders());
        } catch (Exception e) {
            throw new WashingMachineDataFetchException();
        }
    }

    private List<WashingMachine> sortMachines(List<WashingMachine> machines) {
        return machines.stream()
                .sorted(Comparator.comparing(machine -> {
                    int index = SORT_ORDER.indexOf(machine.name());
                    return index != -1 ? index : SORT_ORDER.size();
                }))
                .collect(Collectors.toList());
    }
}