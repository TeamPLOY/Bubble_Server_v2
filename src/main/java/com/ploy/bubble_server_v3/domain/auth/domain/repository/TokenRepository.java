package com.ploy.bubble_server_v3.domain.auth.domain.repository;

import com.ploy.bubble_server_v3.domain.auth.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUserIdAndDeviceToken(Long userId, String deviceToken);

    Optional<Token> findByRefreshToken(String refreshToken);

}
