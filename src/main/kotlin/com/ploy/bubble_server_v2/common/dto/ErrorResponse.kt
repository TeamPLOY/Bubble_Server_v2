package com.ploy.bubble_server_v2.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

// API 응답에서 에러 정보를 표현하는 데이터 클래스
data class ErrorResponse(
    val message: String,  // 에러 메시지
    val path: String,     // 에러가 발생한 요청의 경로
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    val time: LocalDateTime,  // 에러 발생 시간
    val inputErrors: List<FieldError>? = null  // 입력 필드의 에러 정보 리스트
) {
    // 입력 필드의 에러 정보를 표현하는 데이터 클래스
    data class FieldError(
        val field: String,          // 에러가 발생한 필드명
        val rejectedValue: Any?,    // 해당 필드에서 거부된 값
        val message: String         // 필드 에러 메시지
    )

    companion object {
        // ErrorResponse 객체를 생성하는 정적 메서드
        fun of(message: String, path: String, inputErrors: List<FieldError>? = null): ErrorResponse {
            return ErrorResponse(message, path, LocalDateTime.now(), inputErrors)
        }
    }

    // 에러 응답의 문자열 표현을 오버라이드
    override fun toString(): String {
        return "ErrorResponse(message='$message', path='$path', time=$time, inputErrors=$inputErrors)"
    }
}
