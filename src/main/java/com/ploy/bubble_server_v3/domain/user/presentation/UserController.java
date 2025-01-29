package com.ploy.bubble_server_v3.domain.user.presentation;

import com.ploy.bubble_server_v3.domain.user.presentation.dto.UpdatePasswordRequest;
import com.ploy.bubble_server_v3.domain.user.service.CommandUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.ploy.bubble_server_v3.common.util.AuthenticationUtil.getMemberId;

@Tag(name = "유저 정보 변경")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('USER')")
public class UserController {
    private final CommandUserService commandUserService;

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordRequest req)
    {
        commandUserService.updatePassword(getMemberId(), req);

        return ResponseEntity.noContent().build();
    }
}
