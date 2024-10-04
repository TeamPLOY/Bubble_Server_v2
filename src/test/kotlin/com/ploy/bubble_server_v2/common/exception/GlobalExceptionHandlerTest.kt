//package com.ploy.bubble_server_v2.common.exception
//
//import com.fasterxml.jackson.databind.exc.InvalidFormatException
//import com.ploy.bubble_server_v2.common.dto.ErrorResponse
//import org.springframework.core.MethodParameter
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.validation.ConstraintViolation
//import jakarta.validation.ConstraintViolationException
//import jakarta.validation.Path
//import org.junit.jupiter.api.Test
//import org.mockito.Mockito
//import org.springframework.http.HttpStatus
//import org.springframework.security.access.AccessDeniedException
//import org.springframework.validation.BindException
//import org.springframework.validation.BindingResult
//import org.springframework.validation.FieldError
//import org.springframework.web.bind.MethodArgumentNotValidException
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
//import java.lang.reflect.Executable
//import kotlin.test.assertEquals
//import kotlin.test.assertNotNull
//
//class GlobalExceptionHandlerTest {
//
//    private val globalExceptionHandler = GlobalExceptionHandler()
//
//    @Test
//    fun `handleIllegalArgumentException should return BAD_REQUEST response`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val exception = IllegalArgumentException("Invalid argument")
//        val response = globalExceptionHandler.handleIllegalArgumentException(request, exception)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals("잘못된 입력 값입니다.", response.body?.message)
//        assertEquals("/test", response.body?.path)
//        assertEquals(null, response.body?.inputErrors)
//    }
//
//    @Test
//    fun `handleMethodArgumentNotValidException should return BAD_REQUEST response`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val bindingResult = Mockito.mock(BindingResult::class.java)
//        val fieldError = FieldError("objectName", "field", "error message")
//        Mockito.`when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))
//
//        val exception = MethodArgumentNotValidException(
//            Mockito.mock(MethodParameter::class.java),
//            bindingResult
//        )
//
//        val response = globalExceptionHandler.handleMethodArgumentNotValidException(request, exception)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals("잘못된 입력 값입니다.", response.body?.message)
//        assertEquals("/test", response.body?.path)
//        assertEquals(1, response.body?.inputErrors?.size)
//        assertEquals("field", response.body?.inputErrors?.first()?.field)
//        assertEquals("error message", response.body?.inputErrors?.first()?.message)
//    }
//
//    @Test
//    fun `handleConstraintViolationException should return BAD_REQUEST response`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val constraintViolation = Mockito.mock(ConstraintViolation::class.java)
//
//        val propertyPath = Mockito.mock(Path::class.java)
//        Mockito.`when`(propertyPath.toString()).thenReturn("field")
//        Mockito.`when`(constraintViolation.propertyPath).thenReturn(propertyPath)
//        Mockito.`when`(constraintViolation.invalidValue).thenReturn("invalid")
//        Mockito.`when`(constraintViolation.message).thenReturn("Invalid value")
//
//        val exception = ConstraintViolationException(setOf(constraintViolation))
//        val response = globalExceptionHandler.handleConstraintViolationException(request, exception)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals("잘못된 입력 값입니다.", response.body?.message)
//        assertEquals("/test", response.body?.path)
//        assertEquals(1, response.body?.inputErrors?.size)
//        assertEquals("field", response.body?.inputErrors?.first()?.field)
//        assertEquals("invalid", response.body?.inputErrors?.first()?.rejectedValue)
//        assertEquals("Invalid value", response.body?.inputErrors?.first()?.message)
//    }
//
//    @Test
//    fun `handleBindException should return BAD_REQUEST response`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val bindingResult = Mockito.mock(BindingResult::class.java)
//        val fieldError = FieldError("objectName", "field", "error message")
//        Mockito.`when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))
//
//        val exception = BindException(bindingResult)
//        val response = globalExceptionHandler.handleBindException(request, exception)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals("잘못된 입력 값입니다.", response.body?.message)
//        assertEquals("/test", response.body?.path)
//        assertEquals(1, response.body?.inputErrors?.size)
//        assertEquals("field", response.body?.inputErrors?.first()?.field)
//        assertEquals("error message", response.body?.inputErrors?.first()?.message)
//    }
//
//    @Test
//    fun `handleMethodArgumentTypeMismatchException should return BAD_REQUEST response`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val methodParameter = Mockito.mock(MethodParameter::class.java)
//        val executable = Mockito.mock(Executable::class.java)
//        Mockito.`when`(methodParameter.executable).thenReturn(executable)
//
//        val exception = MethodArgumentTypeMismatchException(
//            String::class.java,
//            null,
//            "value",
//            methodParameter,
//            RuntimeException("Cause of the error")
//        )
//        val response = globalExceptionHandler.handleMethodArgumentTypeMismatchException(request, exception)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals("Invalid argument type", response.body?.message)
//        assertEquals("/test", response.body?.path)
//        assertEquals(null, response.body?.inputErrors)
//    }
//
//    @Test
//    fun `handleInvalidFormatException should return BAD_REQUEST response`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val exception = InvalidFormatException(null, "Invalid format", null, null)
//        val response = globalExceptionHandler.handleInvalidFormatException(request, exception)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals("Unknown error", response.body?.message)
//        assertEquals("/test", response.body?.path)
//        assertEquals(null, response.body?.inputErrors)
//    }
//
//    @Test
//    fun `handleNullPointerException should return BAD_REQUEST response`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val exception = NullPointerException("Null pointer exception")
//        val response = globalExceptionHandler.handleNullPointerException(request, exception)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals("널 포인터 예외가 발생했습니다", response.body?.message)
//        assertEquals("/test", response.body?.path)
//        assertEquals(null, response.body?.inputErrors)
//    }
//
//    @Test
//    fun `handleBusinessException should return appropriate response`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val businessException = BusinessException(ErrorCode.BAD_REQUEST)
//        val response = globalExceptionHandler.handleBusinessException(request, businessException)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals("잘못된 입력 값입니다.", response.body?.message)
//        assertEquals("/test", response.body?.path)
//        assertEquals(null, response.body?.inputErrors)
//    }
//
//    @Test
//    fun `handleAccessDeniedException should throw AccessDeniedException`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val exception = AccessDeniedException("Access Denied")
//        val thrown = org.junit.jupiter.api.assertThrows<AccessDeniedException> {
//            globalExceptionHandler.handleAccessDeniedException(request, exception)
//        }
//
//        assertEquals("Access Denied", thrown.message)
//    }
//
//    @Test
//    fun `handleRuntimeException should return BAD_REQUEST response`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val exception = RuntimeException("Runtime exception")
//        val response = globalExceptionHandler.handleRuntimeException(request, exception)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals("런타임 예외가 발생했습니다", response.body?.message)
//        assertEquals("/test", response.body?.path)
//        assertEquals(null, response.body?.inputErrors)
//    }
//
//    @Test
//    fun `handleException should return BAD_REQUEST response`() {
//        val request = Mockito.mock(HttpServletRequest::class.java)
//        Mockito.`when`(request.requestURI).thenReturn("/test")
//
//        val exception = Exception("Unknown exception")
//        val response = globalExceptionHandler.handleException(request, exception)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals("알 수 없는 오류가 발생했습니다", response.body?.message)
//        assertEquals("/test", response.body?.path)
//        assertEquals(null, response.body?.inputErrors)
//    }
//}
