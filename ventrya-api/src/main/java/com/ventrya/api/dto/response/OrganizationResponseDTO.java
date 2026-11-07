package com.ventrya.api.dto.response;

import java.time.LocalDateTime;

public record OrganizationResponseDTO(
        Long id,
        String name,
        String email,
        String description,
        String webSite,
        LocalDateTime createdAt
) {
}
