package com.ploy.bubble_server_v3.domain.machine.util;

import com.ploy.bubble_server_v3.domain.machine.domain.WashingMachine;
import com.ploy.bubble_server_v3.domain.machine.infra.dto.response.vo.Device;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DeviceProcessor {

    private final Map<String, String> washTowerMap = new HashMap<>();

    public DeviceProcessor() {
        washTowerMap.put("세탁기", "B31 세탁기3");
        washTowerMap.put("세탁기1", "B31 세탁기4");
        washTowerMap.put("세탁기2", "B32 세탁기3");
        washTowerMap.put("세탁기3", "B32 세탁기4");
        washTowerMap.put("세탁기4", "B41 세탁기3");
        washTowerMap.put("세탁기5", "B41 세탁기4");
        washTowerMap.put("세탁기6", "B42 세탁기3");
        washTowerMap.put("세탁기7", "B42 세탁기4");
    }

    public void processAllDevices(Device device, List<WashingMachine> result) {
        String alias = device.alias();
        if (alias == null) {
            return;
        }

        Integer remainTime = extractRemainTime(device);

        if (washTowerMap.containsKey(alias)) {
            alias = washTowerMap.get(alias);
        }

        if (remainTime != null) {
            result.add(new WashingMachine(alias, remainTime));
        }
    }

    public void processDeviceByRoom(Device device, List<WashingMachine> result, String roomId) {
        String alias = device.alias();
        if (alias == null) {
            return;
        }

        Integer remainTime = extractRemainTime(device);

        if (washTowerMap.containsKey(alias)) {
            alias = washTowerMap.get(alias);
        }

        String cleanAlias = alias.replace(roomId, "").trim();

        if (alias.contains(roomId) && remainTime != null) {
            result.add(new WashingMachine(cleanAlias, remainTime));
        }
    }

    private Integer extractRemainTime(Device device) {
        if (device.snapshot() != null &&
                device.snapshot().washerDryer() != null) {
            return device.snapshot().washerDryer().remainTimeMinute();
        }
        return null;
    }
}
