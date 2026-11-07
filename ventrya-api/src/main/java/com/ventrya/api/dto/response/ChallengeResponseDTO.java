package com.ventrya.api.dto.response;

import com.ventrya.api.entity.enums.ChallengeStatus;
import com.ventrya.api.entity.enums.Difficulty;
import com.ventrya.api.entity.enums.ProfessionalArea;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ChallengeResponseDTO(
        Long id,
        String title,
        String description,
        ProfessionalArea professionalArea,
        Difficulty difficulty,
        String requirements,
        LocalDate deadline,
        ChallengeStatus status,
        Long organizationId,
        String organizationName,
        LocalDateTime createdAt
) {
}
