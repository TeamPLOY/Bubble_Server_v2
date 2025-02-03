package com.ploy.bubble_server_v3.domain.auth.service.implementation;

import com.ploy.bubble_server_v3.domain.auth.domain.Token;
import com.ploy.bubble_server_v3.domain.auth.domain.repository.TokenRepository;
import com.ploy.bubble_server_v3.common.jwt.dto.TokenResponse;
import com.ploy.bubble_server_v3.common.jwt.Jwt;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthUpdater {
    private final Jwt jwt;
    private final TokenRepository tokenRepository;

    @Transactional
    public TokenResponse publishToken(Users user, String deviceToken, Token existingToken) {
        Jwt.Claims claims = Jwt.Claims.from(user.getId(), new Role[]{Role.USER});
        TokenResponse tokenResponse = jwt.generateAllToken(claims);

        if (existingToken != null) {
            existingToken.updateRefreshToken(tokenResponse.refreshToken());

        } else {
            existingToken = Token.builder()
                    .user(user)
                    .deviceToken(deviceToken)
                    .refreshToken(tokenResponse.refreshToken())
                    .build();
        }

        tokenRepository.save(existingToken);

        return tokenResponse;
    }
}
