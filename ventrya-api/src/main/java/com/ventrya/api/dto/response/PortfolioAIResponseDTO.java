package com.ventrya.api.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record PortfolioAIResponseDTO(

        @NotBlank(message = "O título do portfólio é obrigatório.")
        @Size(max = 255,
                message = "O título deve conter no máximo 255 caracteres.")
        String title,

        @NotBlank(message = "A descrição profissional é obrigatória.")
        @Size(max = 4000,
                message = "A descrição deve conter no máximo 4000 caracteres.")
        String generatedDescription,

        @NotBlank(message = "As habilidades desenvolvidas são obrigatórias.")
        @Size(max = 2000,
                message = "As habilidades devem conter no máximo 2000 caracteres.")
        String skills
) {
}
