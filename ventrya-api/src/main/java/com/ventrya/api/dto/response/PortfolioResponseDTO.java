package com.ventrya.api.dto.response;

import com.ventrya.api.entity.enums.PortfolioStatus;

import java.time.LocalDateTime;

public record PortfolioResponseDTO(
        Long id,
        Long studentId,
        Long submissionId,
        String title,
        String generatedDescription,
        String skills,
        PortfolioStatus status,
        LocalDateTime createdAt
) {
}
