package com.ploy.bubble_server_v3.domain.email.presentation;

import com.ploy.bubble_server_v3.domain.email.presentation.dto.request.EmailCertificationRequest;
import com.ploy.bubble_server_v3.domain.email.presentation.dto.request.EmailSendRequest;
import com.ploy.bubble_server_v3.domain.email.service.CommandEmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "이메일 인증")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/email")

public class EmailController {
    private final CommandEmailService commandEmailService;

    @Operation(summary = "이메일 전송")
    @PostMapping("send")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailSendRequest emailSendRequest) {
        commandEmailService.sendEmail(emailSendRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "이메일 인증")
    @PostMapping("/certification")
    public ResponseEntity<Boolean> certificationEmail(@RequestBody EmailCertificationRequest emailCertificationRequest){
        return ResponseEntity.ok(commandEmailService.certificationEmail(emailCertificationRequest));
    }
}