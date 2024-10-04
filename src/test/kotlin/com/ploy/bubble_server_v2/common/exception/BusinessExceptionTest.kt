package com.ploy.bubble_server_v2.common.exception

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BusinessExceptionTest {

    @Test
    fun `test BusinessException`() {
        val errorCode = ErrorCode.BAD_REQUEST
        val exception = BusinessException(errorCode)

        assertEquals("잘못된 입력 값입니다.", exception.message)
    }
}
