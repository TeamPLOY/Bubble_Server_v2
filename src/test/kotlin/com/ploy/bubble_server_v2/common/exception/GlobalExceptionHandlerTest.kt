package com.ploy.bubble_server_v2.common.exception

import com.ploy.bubble_server_v2.common.dto.ErrorResponse
import com.sun.org.apache.bcel.internal.classfile.MethodParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.springframework.validation.BindException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

class GlobalExceptionHandlerTest {

    private val globalExceptionHandler = GlobalExceptionHandler()
    private val request: HttpServletRequest = mock(HttpServletRequest::class.java)

    @Test
    fun `handleIllegalArgumentException should return BAD_REQUEST`() {
        `when`(request.requestURI).thenReturn("/test")

        val exception = IllegalArgumentException("Invalid argument")
        val response = globalExceptionHandler.handleIllegalArgumentException(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorResponse.of("BAD_REQUEST", "/test", null), response.body)
    }

    @Test
    fun `handleMethodArgumentNotValidException should return BAD_REQUEST`() {
        `when`(request.requestURI).thenReturn("/test")

        val bindingResult = mock(org.springframework.validation.BindingResult::class.java)
        val exception = MethodArgumentNotValidException(null, bindingResult)

        `when`(bindingResult.fieldErrors).thenReturn(listOf())

        val response = globalExceptionHandler.handleMethodArgumentNotValidException(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorResponse.of("BAD_REQUEST", "/test", null), response.body)
    }

    @Test
    fun `handleConstraintViolationException should return BAD_REQUEST`() {
        `when`(request.requestURI).thenReturn("/test")

        val violation: ConstraintViolation<*> = mock(ConstraintViolation::class.java)
        `when`(violation.invalidValue).thenReturn("invalid")
        `when`(violation.message).thenReturn("Constraint violation")
        `when`(violation.propertyPath).thenReturn(mock(jakarta.validation.Path::class.java))

        val violations: Set<ConstraintViolation<*>> = setOf(violation)
        val exception = ConstraintViolationException(violations)

        val response = globalExceptionHandler.handleConstraintViolationException(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorResponse.of("BAD_REQUEST", "/test", null), response.body)
    }

    @Test
    fun `handleBindException should return BAD_REQUEST`() {
        `when`(request.requestURI).thenReturn("/test")

        val bindingResult = mock(org.springframework.validation.BindingResult::class.java)
        val exception = BindException(bindingResult)

        val response = globalExceptionHandler.handleBindException(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorResponse.of("BAD_REQUEST", "/test", null), response.body)
    }

    @Test
    fun `handleMethodArgumentTypeMismatchException should return BAD_REQUEST`() {
        `when`(request.requestURI).thenReturn("/test")

        // Mock MethodParameter 객체 생성
        val methodParameter: MethodParameter = mock(MethodParameter::class.java)

        val exception = MethodArgumentTypeMismatchException(
            methodParameter,
            String::class.java,
            "argName",
            null,
            null
        )

        val response = globalExceptionHandler.handleMethodArgumentTypeMismatchException(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorResponse.of("Invalid argument type", "/test", listOf()), response.body)
    }

    @Test
    fun `handleNullPointerException should return BAD_REQUEST`() {
        `when`(request.requestURI).thenReturn("/test")

        val exception = NullPointerException("Null pointer")
        val response = globalExceptionHandler.handleNullPointerException(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorResponse.of("널 포인터 예외가 발생했습니다", "/test", null), response.body)
    }

    @Test
    fun `handleRuntimeException should return BAD_REQUEST`() {
        `when`(request.requestURI).thenReturn("/test")

        val exception = RuntimeException("Runtime exception")
        val response = globalExceptionHandler.handleRuntimeException(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorResponse.of("런타임 예외가 발생했습니다", "/test", null), response.body)
    }

    @Test
    fun `handleException should return BAD_REQUEST`() {
        `when`(request.requestURI).thenReturn("/test")

        val exception = Exception("Unknown error")
        val response = globalExceptionHandler.handleException(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorResponse.of("알 수 없는 오류가 발생했습니다", "/test", null), response.body)
    }
}
