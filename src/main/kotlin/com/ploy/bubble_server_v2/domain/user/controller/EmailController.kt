package com.ploy.bubble_server_v2.domain.user.controller

import com.ploy.bubble_server_v2.domain.facade.EmailFacade
import com.ploy.bubble_server_v2.domain.user.model.dto.request.EmailCheckRequest
import com.ploy.bubble_server_v2.domain.user.model.dto.request.EmailRequest
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@io.swagger.v3.oas.annotations.tags.Tag(name = "이메일 인증")
@lombok.extern.slf4j.Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
class EmailController {
    private val emailFacade: EmailFacade? = null

    @io.swagger.v3.oas.annotations.Operation(summary = "이메일 전송")
    @PostMapping("")
    fun sendEmail(@org.springframework.web.bind.annotation.RequestBody emailRequest: EmailRequest): ResponseEntity<Void> {
        emailFacade!!.sendEmail(emailRequest)
        return ResponseEntity.noContent().build<Void>()
    }

    // 이 이메일은 스케줄러 사용해서 5분 뒤 디비에서 삭제
    @io.swagger.v3.oas.annotations.Operation(summary = "이메일 인증")
    @PostMapping("/check")
    fun certificationEmail(@org.springframework.web.bind.annotation.RequestBody emailCheckRequest: EmailCheckRequest?): ResponseEntity<Boolean> {
        return ResponseEntity.ok<Boolean>(emailFacade!!.certificationEmail(emailCheckRequest))
    }
}