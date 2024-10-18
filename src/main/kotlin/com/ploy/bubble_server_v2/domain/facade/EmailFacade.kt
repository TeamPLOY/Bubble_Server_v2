package com.ploy.bubble_server_v2.domain.facade

import com.ploy.bubble_server_v2.domain.user.model.dto.request.EmailCheckRequest
import com.ploy.bubble_server_v2.domain.user.model.dto.request.EmailRequest
import com.ploy.bubble_server_v2.domain.user.service.EmailService
import lombok.RequiredArgsConstructor

@org.springframework.stereotype.Component
@RequiredArgsConstructor
class EmailFacade {
    private val emailService: EmailService? = null

    @org.springframework.transaction.annotation.Transactional
    fun sendEmail(req: EmailRequest) {
        emailService.sendEmail(req.email())
    }

    @org.springframework.transaction.annotation.Transactional
    fun certificationEmail(req: EmailCheckRequest): Boolean {
        return emailService.certificationEmail(req.code(), req.email())
    }
}
