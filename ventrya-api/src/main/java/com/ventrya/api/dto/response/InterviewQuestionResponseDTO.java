package com.ventrya.api.dto.response;

public record InterviewQuestionResponseDTO(
        Long id,
        Integer questionNumber,
        String question,
        String answer,
        String aiFeedback
) {
}
