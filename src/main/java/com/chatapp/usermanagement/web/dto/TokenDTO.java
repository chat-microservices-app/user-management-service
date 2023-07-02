package com.chatapp.usermanagement.web.dto;

import java.util.UUID;

public record TokenDTO(
        UUID userId,
        String accessToken,
        String refreshToken

) {
}
