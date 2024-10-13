package com.ploy.bubble_server_v2.common.exception


import com.fasterxml.jackson.databind.ObjectMapper  // JSON 데이터를 Java 객체로 변환하거나 Java 객체를 JSON으로 변환하는 데 사용됩니다.
import com.fasterxml.jackson.databind.SerializationFeature  // JSON 직렬화 옵션을 설정합니다.
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule  // Java 8 날짜 및 시간 API를 JSON으로 변환할 때 필요한 모듈
import com.ploy.bubble_server_v2.common.dto.ErrorResponse  // 에러 응답을 정의하는 DTO 클래스
import jakarta.servlet.http.HttpServletRequest  // HTTP 요청을 표현하는 클래스
import jakarta.servlet.http.HttpServletResponse  // HTTP 응답을 표현하는 클래스
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException  // 권한이 없어서 액세스가 거부된 경우 발생하는 예외
import org.springframework.security.web.access.AccessDeniedHandler  // 액세스 거부 상황을 처리하는 인터페이스
import java.io.IOException  // 입출력 관련 예외 처리
import java.io.PrintWriter  // 응답을 출력하는 데 사용되는 클래스
import org.springframework.http.MediaType.APPLICATION_JSON  // 응답의 콘텐츠 타입 설정

class CustomAccessDeniedHandler : AccessDeniedHandler {

    private val log = LoggerFactory.getLogger(CustomAccessDeniedHandler::class.java)


    // 액세스 거부 예외를 처리하는 메서드
    @Throws(IOException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        // 로그에 요청 URL, 예외 클래스, 예외 메시지를 기록
        log.info("URL = {}, Exception = {}, Message = {}",
            request.requestURI,
            accessDeniedException.javaClass.simpleName,
            accessDeniedException.message
        )

        // 응답의 문자 인코딩과 콘텐츠 타입 설정
        response.characterEncoding = "UTF-8"
        response.contentType = APPLICATION_JSON.toString()  // 응답 타입을 JSON으로 설정
        response.status = HttpServletResponse.SC_FORBIDDEN  // HTTP 상태 코드를 403 (Forbidden)으로 설정

        // 에러 응답 객체 생성
        val errorResponse = ErrorResponse.of(
            accessDeniedException.message ?: "Access Denied",  // 에러 메시지, 기본값 설정
            request.requestURI,  // 요청 URI
            null  // 입력 에러 정보 (없음)
        )

        // Jackson ObjectMapper를 사용하여 ErrorResponse 객체를 JSON 문자열로 변환
        val objectMapper = ObjectMapper().apply {
            registerModule(JavaTimeModule())  // Java 8 날짜 및 시간 API 모듈 등록
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)  // 날짜를 타임스탬프로 변환하지 않도록 설정
        }

        // 응답에 JSON 문자열을 작성
        val writer: PrintWriter = response.writer
        writer.write(objectMapper.writeValueAsString(errorResponse))
        writer.flush()  // 버퍼를 플러시하여 응답을 클라이언트에 전송
        writer.close()  // PrintWriter 닫기
    }
}
