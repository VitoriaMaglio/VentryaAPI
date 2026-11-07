package com.ventrya.api.dto.request;
import jakarta.validation.constraints.*;
public record FeedbackRequestDTO(
        @NotNull(message = "Selecione uma entrega para avaliar.")
        @Positive(message = "O ID da entrega deve ser positivo.")
        Long submissionId,

        @NotBlank(message = "O comentário da avaliação é obrigatório.")
        @Size(max = 2000,
                message = "O comentário deve conter no máximo 2000 caracteres.")
        String comment,

        @NotNull(message = "Informe uma nota para a entrega.")
        @Min(value = 1, message = "A nota mínima é 1.")
        @Max(value = 5, message = "A nota máxima é 5.")
        Integer rating,

        @Size(max = 2000,
                message = "Os pontos fortes devem conter no máximo 2000 caracteres.")
        String strengths,

        @Size(max = 2000,
                message = "As sugestões de melhoria devem conter no máximo 2000 caracteres.")
        String improvements

) {
}
