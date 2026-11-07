package com.ventrya.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ApplicationRequestDTO(

        @NotNull(message = "O ID do desafio é obrigatório.")
        @Positive(message = "O ID do desafio deve ser positivo.")
        Long challengeId

) {
}
