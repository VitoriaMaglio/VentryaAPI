package com.ventrya.api.dto.response;

import jakarta.validation.constraints.NotBlank;

public record InterviewAIResponseDTO(
        @NotBlank(message = "O feedback da entrevista não pode estar vazio.")
        String aiFeedback
) {
}
