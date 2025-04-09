package com.ploy.bubble_server_v3.domain.machine.exception;

import com.ploy.bubble_server_v3.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class WashingMachineDataFetchException extends BaseException {
    public WashingMachineDataFetchException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "WASHING_MACHINE_DATA_FETCH_FAILED", "세탁기 데이터를 가져오는 데 실패했습니다.");
    }
}
