package com.ventrya.api.dto.response;

import com.ventrya.api.entity.enums.InterviewStatus;
import com.ventrya.api.entity.enums.ProfessionalArea;

import java.time.LocalDateTime;
import java.util.List;

public record InterviewResponseDTO(
        Long id,
        Long studentId,
        ProfessionalArea professionalArea,
        InterviewStatus status,
        String overallFeedback,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        List<InterviewQuestionResponseDTO> questions
) {
}
