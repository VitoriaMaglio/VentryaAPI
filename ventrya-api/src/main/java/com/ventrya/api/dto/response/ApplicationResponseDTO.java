package com.ventrya.api.dto.response;

import com.ventrya.api.entity.enums.Status;

import java.time.LocalDateTime;

public record ApplicationResponseDTO(
        Long id,
        Long studentId,
        String studentName,
        Long challengeId,
        String challengeTitle,
        Status status,
        LocalDateTime appliedAt
) {
}
