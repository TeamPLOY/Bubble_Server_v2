package com.ploy.bubble_server_v2.common.exception

class BusinessException(val errorCode: ErrorCode) : RuntimeException(errorCode.message) {
    // 비즈니스 로직에서 발생한 에러를 식별하기 위한 에러 코드

    // 생성자: 에러 코드를 인자로 받아 RuntimeException의 생성자에 메시지를 전달합니다.
}

