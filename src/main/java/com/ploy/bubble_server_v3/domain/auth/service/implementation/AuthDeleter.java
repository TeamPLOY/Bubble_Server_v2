package com.ploy.bubble_server_v3.domain.auth.service.implementation;

import com.ploy.bubble_server_v3.domain.auth.domain.Token;
import org.springframework.stereotype.Service;

@Service
public class AuthDeleter {
    public void deleteRefreshToken(Token token) {
        token.deleteRefreshToken();
    }
}