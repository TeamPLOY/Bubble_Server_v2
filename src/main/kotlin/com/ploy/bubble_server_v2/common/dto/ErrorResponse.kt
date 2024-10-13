package com.ploy.bubble_server_v2.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

// API 응답에서 에러 정보를 표현하는 데이터 클래스
data class ErrorResponse(
    val message: String,  // 에러 메시지
    val path: String,     // 에러가 발생한 요청의 경로
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    val time: LocalDateTime = LocalDateTime.now(),  // 에러 발생 시간, 기본값으로 현재 시간을 설정
    val inputErrors: List<FieldError>? = null  // 입력 필드의 에러 정보 리스트, 기본값으로 null
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
            // 현재 시간으로 ErrorResponse 객체를 생성하여 반환
            return ErrorResponse(message, path, LocalDateTime.now(), inputErrors)
        }
    }
}
