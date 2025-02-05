package com.ploy.bubble_server_v3.domain.auth.presentation;

import com.ploy.bubble_server_v3.common.jwt.dto.TokenResponse;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.LoginRequest;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.QuitRequest;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.SignUpRequest;
import com.ploy.bubble_server_v3.domain.auth.presentation.dto.request.TokenRefreshRequest;
import com.ploy.bubble_server_v3.domain.auth.service.CommandAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.ploy.bubble_server_v3.common.util.AuthenticationUtil.getUserId;

@Tag(name = "인증/인가")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final CommandAuthService commandAuthService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @PermitAll
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest req) {
        commandAuthService.signUp(req);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(commandAuthService.login(req));
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Void> logout(@Valid @RequestBody TokenRefreshRequest req) {
        commandAuthService.logout(req);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Void> quit(@Valid @RequestBody QuitRequest req) {
        commandAuthService.quit(getUserId(),req);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "토큰 갱신")
    @PostMapping("refresh")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody TokenRefreshRequest req) {
        return ResponseEntity.ok(commandAuthService.refresh(req));
    }
}
