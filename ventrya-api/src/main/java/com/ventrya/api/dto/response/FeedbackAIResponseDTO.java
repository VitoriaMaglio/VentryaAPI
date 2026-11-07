package com.ventrya.api.dto.response;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.*;
public record FeedbackAIResponseDTO(

        @NotBlank(message = "A avaliação da IA não pode estar vazia.")
                String comment,

        @NotNull(message = "A nota da avaliação é obrigatória.")
        @Min(value = 1, message = "A nota mínima é 1.")
        @Max(value = 5, message = "A nota máxima é 5.")
        Integer rating,

        @NotBlank(message = "Os pontos fortes são obrigatórios.")
        String strengths,

        @NotBlank(message = "As sugestões de melhoria são obrigatórias.")
        String improvements

) {}
