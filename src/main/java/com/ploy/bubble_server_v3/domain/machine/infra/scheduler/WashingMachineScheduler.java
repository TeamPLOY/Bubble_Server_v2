package com.ploy.bubble_server_v3.domain.machine.infra.scheduler;

import com.ploy.bubble_server_v3.domain.machine.domain.WashingMachine;
import com.ploy.bubble_server_v3.domain.machine.service.WashingMachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WashingMachineScheduler {

    private final WashingMachineService washingMachineService;

    // 평일: 20시 ~ 익일 1시 (매 1분마다 실행)
    @Scheduled(cron = "0 0-59 20-23 * * MON-FRI", zone = "Asia/Seoul")
    @Scheduled(cron = "0 0-59 0 * * TUE-SAT", zone = "Asia/Seoul")
    public void updateWashingMachineStatusWeekdays() {
        executeTask();
    }

    // 일요일: 18시 ~ 익일 1시 (매 1분마다 실행)
    @Scheduled(cron = "0 0-59 18-23 * * SUN", zone = "Asia/Seoul")
    @Scheduled(cron = "0 0-59 0 * * MON", zone = "Asia/Seoul")
    public void updateWashingMachineStatusSunday() {
        executeTask();
    }

    private void executeTask() {
        log.info("모든 사용자의 세탁기 및 건조기 상태를 업데이트하는 중...");

        List<WashingMachine> washingMachineList = washingMachineService.getAllWashingMachines();

        washingMachineService.saveWashingMachines(washingMachineList);
        log.info("상태 업데이트가 완료되었습니다.");

    }
}