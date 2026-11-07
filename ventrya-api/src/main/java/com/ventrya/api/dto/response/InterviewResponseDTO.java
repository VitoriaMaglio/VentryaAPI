package com.ventrya.api.dto.response;

import com.ventrya.api.entity.enums.ProfessionalArea;

import java.time.LocalDateTime;

public record InterviewResponseDTO(
        Long id,
        Long studentId,
        ProfessionalArea professionalArea,
        String question,
        String answer,
        String aiFeedback,
        LocalDateTime createdAt
) {
}
