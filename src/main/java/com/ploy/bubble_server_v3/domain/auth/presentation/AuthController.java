package com.ploy.bubble_server_v3.domain.auth.presentation;

import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.SignUpRequest;
import com.ploy.bubble_server_v3.domain.auth.service.CommandAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증/인가")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@PermitAll
public class AuthController {

    private final CommandAuthService commandAuthService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest req) {
        commandAuthService.signUp(req);
        return ResponseEntity.noContent().build();
    }
}
