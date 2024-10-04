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

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(request: HttpServletRequest?, e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return createErrorResponse(request, e, ErrorCode.BAD_REQUEST.message, null)
    }

    // Handle MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(request: HttpServletRequest?, e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return createErrorResponse(
            request,
            e,
            ErrorCode.BAD_REQUEST.message,
            makeFieldErrorsFromBindingResult(e.bindingResult)
        )
    }

    // Handle ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(request: HttpServletRequest?, e: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        return createErrorResponse(
            request,
            e,
            ErrorCode.BAD_REQUEST.message,
            makeFieldErrorsFromConstraintViolations(e.constraintViolations)
        )
    }

    // Handle BindException
    @ExceptionHandler(BindException::class)
    fun handleBindException(request: HttpServletRequest?, e: BindException): ResponseEntity<ErrorResponse> {
        return createErrorResponse(
            request,
            e,
            ErrorCode.BAD_REQUEST.message,
            makeFieldErrorsFromBindingResult(e.bindingResult)
        )
    }

    // Handle MethodArgumentTypeMismatchException
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(request: HttpServletRequest?, e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        val fieldErrors = listOf(
            ErrorResponse.FieldError(
                e.name ?: "unknown",
                e.value.toString(),
                e.parameter?.parameterName ?: "unknown"
            )
        )
        return createErrorResponse(request, e, "Invalid argument type", fieldErrors)
    }

    // Handle InvalidFormatException
    @ExceptionHandler(InvalidFormatException::class)
    fun handleInvalidFormatException(request: HttpServletRequest?, e: InvalidFormatException): ResponseEntity<ErrorResponse> {
        return createErrorResponse(request, e, e.message ?: "Unknown error", null)
    }

    // Handle NullPointerException
    @ExceptionHandler(NullPointerException::class)
    fun handleNullPointerException(request: HttpServletRequest?, e: NullPointerException): ResponseEntity<ErrorResponse> {
        return createErrorResponse(request, e, "A null pointer exception occurred", null)
    }

    // Handle BusinessException
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(request: HttpServletRequest?, e: BusinessException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(e.errorCode.status)
            .body(ErrorResponse.of(e.errorCode.message, request?.requestURI ?: "Unknown URI", null))
    }

    // Handle AccessDeniedException
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(request: HttpServletRequest?, e: AccessDeniedException): ResponseEntity<ErrorResponse> {
        logInfo(e, request?.requestURI ?: "Unknown URI")
        throw AccessDeniedException(e.message)
    }

    // Handle RuntimeException
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(request: HttpServletRequest?, e: RuntimeException): ResponseEntity<ErrorResponse> {
        return createErrorResponse(request, e, "A runtime exception occurred", null)
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception::class)
    fun handleException(request: HttpServletRequest?, e: Exception): ResponseEntity<ErrorResponse> {
        return createErrorResponse(request, e, "An unknown error occurred", null)
    }

    // Create an error response for different exceptions
    private fun createErrorResponse(
        request: HttpServletRequest?,
        e: Exception,
        message: String,
        fieldErrors: List<ErrorResponse.FieldError>?
    ): ResponseEntity<ErrorResponse> {
        logInfo(e, request?.requestURI ?: "Unknown URI")
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(message, request?.requestURI ?: "Unknown URI", fieldErrors))
    }

    // Convert field errors from BindingResult to a list
    private fun makeFieldErrorsFromBindingResult(bindingResult: BindingResult): List<ErrorResponse.FieldError> {
        return bindingResult.fieldErrors.map { error ->
            ErrorResponse.FieldError(
                error.field,
                error.rejectedValue,
                error.defaultMessage ?: "Invalid value"
            )
        }
    }

    // Convert field errors from ConstraintViolations to a list
    private fun makeFieldErrorsFromConstraintViolations(constraintViolations: Set<ConstraintViolation<*>>): List<ErrorResponse.FieldError> {
        return constraintViolations.map { violation ->
            ErrorResponse.FieldError(
                getFieldFromPath(violation.propertyPath),
                violation.invalidValue.toString(),
                violation.message
            )
        }
    }

    // Extract field name from path
    private fun getFieldFromPath(fieldPath: jakarta.validation.Path): String {
        return fieldPath.last().name
    }

    // Log information at info level
    private fun logInfo(e: Exception, url: String) {
        log.info("URL = {}, Exception = {}, Message = {}", url, e.javaClass.simpleName, e.message)
    }


}
