package com.ventrya.api.dto.response;

public record AuthResponse(
        String token,
        String tokenType,
        UserResponseDTO user
) {
}
