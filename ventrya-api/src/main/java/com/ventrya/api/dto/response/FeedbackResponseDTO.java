package com.ventrya.api.dto.response;

import java.time.LocalDateTime;

public record FeedbackResponseDTO(
        Long id,
        Long submissionId,
        String comment,
        Integer rating,
        String strengths,
        String improvements,
        LocalDateTime createdAt
) {
}
