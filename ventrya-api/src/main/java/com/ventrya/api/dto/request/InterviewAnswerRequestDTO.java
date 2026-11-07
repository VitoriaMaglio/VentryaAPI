package com.ventrya.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InterviewAnswerRequestDTO(
        @NotBlank(message = "Digite sua resposta antes de continuar.")
        @Size(max = 4000,
                message = "A resposta deve conter no máximo 4000 caracteres.")
        String answer
) {
}
