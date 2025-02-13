package com.ploy.bubble_server_v3.domain.email.presentation.dto.request;

public record EmailCertificationRequest(
        String code,
        String email
) {
}