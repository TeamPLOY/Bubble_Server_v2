package com.ploy.bubble_server_v2.common.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.ploy.bubble_server_v2.common.dto.ErrorResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    // IllegalArgumentException 예외 처리
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(request: HttpServletRequest, e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logInfo(e, request.requestURI)

        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(ErrorCode.BAD_REQUEST.message, request.requestURI, null))
    }

    // MethodArgumentNotValidException 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(request: HttpServletRequest, e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logInfo(e, request.requestURI)

        return ResponseEntity.badRequest()
            .body(
                ErrorResponse.of(
                    ErrorCode.BAD_REQUEST.message,
                    request.requestURI,
                    makeFieldErrorsFromBindingResult(e.bindingResult)
                )
            )
    }

    // ConstraintViolationException 예외 처리
    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handleConstraintViolationException(request: HttpServletRequest, e: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        logInfo(e, request.requestURI)

        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(
                ErrorCode.BAD_REQUEST.message,
                request.requestURI,
                makeFieldErrorsFromConstraintViolations(e.constraintViolations)
            ))
    }

    // BindException 예외 처리
    @ExceptionHandler(BindException::class)
    fun handleBindException(request: HttpServletRequest, e: BindException): ResponseEntity<ErrorResponse> {
        logInfo(e, request.requestURI)

        return ResponseEntity.badRequest()
            .body(
                ErrorResponse.of(
                    ErrorCode.BAD_REQUEST.message,
                    request.requestURI,
                    makeFieldErrorsFromBindingResult(e.bindingResult)
                )
            )
    }

    // MethodArgumentTypeMismatchException 예외 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(request: HttpServletRequest, e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        logInfo(e, request.requestURI)

        val errorResponse = ErrorResponse.of(
            e.message ?: "Invalid argument type",
            request.requestURI,
            listOf(
                ErrorResponse.FieldError(
                    e.name ?: "unknown", // null일 경우 "unknown"으로 대체
                    e.value.toString(),
                    e.parameter?.parameterName ?: "unknown" // null일 경우 "unknown"으로 대체
                )
            )
        )
        return ResponseEntity.badRequest().body(errorResponse)
    }

    // InvalidFormatException 예외 처리
    @ExceptionHandler(InvalidFormatException::class)
    protected fun handleInvalidFormatException(
        request: HttpServletRequest, e: InvalidFormatException
    ): ResponseEntity<ErrorResponse> {
        logInfo(e, request.requestURI)

        // HTTP 400 Bad Request 응답 생성
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(e.message ?: "Unknown error", request.requestURI, null))
    }

    // NullPointerException 예외 처리
// NullPointerException 예외 처리
    @ExceptionHandler(NullPointerException::class)
    protected fun handleNullPointerException(request: HttpServletRequest, e: NullPointerException): ResponseEntity<ErrorResponse> {
        logInfo(e, request.requestURI)

        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(e.message ?: "널 포인터 예외가 발생했습니다", request.requestURI, null))
    }

    // BusinessException 예외 처리
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(request: HttpServletRequest, e: BusinessException): ResponseEntity<ErrorResponse> {
        logInfo(e, request.requestURI)

        return ResponseEntity
            .status(e.errorCode.status)
            .body(ErrorResponse.of(e.errorCode.message, request.requestURI, null))
    }

    // AccessDeniedException 예외 처리
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(request: HttpServletRequest, e: AccessDeniedException): ResponseEntity<ErrorResponse> {
        logInfo(e, request.requestURI)
        throw AccessDeniedException(e.message)
    }

    // RuntimeException 예외 처리
// RuntimeException 예외 처리
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(request: HttpServletRequest, e: RuntimeException): ResponseEntity<ErrorResponse> {
        logWarn(e, request.requestURI)

        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(e.message ?: "런타임 예외가 발생했습니다", request.requestURI, null))
    }


    // 모든 예외 처리
// 모든 예외 처리
    @ExceptionHandler(Exception::class)
    fun handleException(request: HttpServletRequest, e: Exception): ResponseEntity<ErrorResponse> {
        logError(e, request.requestURI)

        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(e.message ?: "알 수 없는 오류가 발생했습니다", request.requestURI, null))
    }

    // BindingResult에서 필드 에러를 리스트로 변환
    private fun makeFieldErrorsFromBindingResult(bindingResult: BindingResult): List<ErrorResponse.FieldError> {
        return bindingResult.fieldErrors
            .map { error ->
                ErrorResponse.FieldError(
                    error.field,
                    error.rejectedValue,
                    error.defaultMessage ?: "유효하지 않은 값" // 기본 메시지 제공
                )
            }
    }

    // ConstraintViolation에서 필드 에러를 리스트로 변환
    private fun makeFieldErrorsFromConstraintViolations(constraintViolations: Set<ConstraintViolation<*>>): List<ErrorResponse.FieldError> {
        return constraintViolations.map { violation ->
            ErrorResponse.FieldError(
                getFieldFromPath(violation.propertyPath),
                violation.invalidValue.toString(),
                violation.message
            )
        }
    }

    // 경로에서 필드 이름 추출
    private fun getFieldFromPath(fieldPath: jakarta.validation.Path): String {
        return fieldPath.last().name // Path 인터페이스를 사용하여 필드 이름 가져오기
    }

    // 정보 수준 로그 기록
    private fun logInfo(e: Exception, url: String) {
        log.info("URL = {}, Exception = {}, Message = {}", url, e.javaClass.simpleName, e.message)
    }

    // 경고 수준 로그 기록
    private fun logWarn(e: Exception, url: String) {
        log.warn("URL = {}, Exception = {}, Message = {}", url, e.javaClass.simpleName, e.message)
    }

    // 오류 수준 로그 기록
    private fun logError(e: Exception, url: String) {
        log.error("URL = {}, Exception = {}, Message = {}", url, e.javaClass.simpleName, e.message)
    }
}
