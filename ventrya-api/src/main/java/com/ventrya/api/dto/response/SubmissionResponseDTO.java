package com.ventrya.api.dto.response;

import com.ventrya.api.entity.enums.SubmissionStatus;

import java.time.LocalDateTime;

public record SubmissionResponseDTO(
        Long id,
        Long applicationId,
        String description,
        String fileUrl,
        SubmissionStatus status,
        LocalDateTime submittedAt
) {
}
