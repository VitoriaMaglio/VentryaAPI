package com.ventrya.api.dto.request;
import com.ventrya.api.entity.enums.Difficulty;
import com.ventrya.api.entity.enums.ProfessionalArea;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ChallengeRequestDTO(

        @NotBlank(message = "O título do desafio é obrigatório.")
        @Size(min = 5, max = 255,
                message = "O título deve conter entre 5 e 255 caracteres.")
        String title,

        @NotBlank(message = "A descrição do desafio é obrigatória.")
        @Size(min = 20, max = 2000,
                message = "A descrição deve conter entre 20 e 2000 caracteres.")
        String description,

        @NotNull(message = "Selecione a área profissional do desafio.")
        ProfessionalArea professionalArea,

        @NotNull(message = "Selecione o nível de dificuldade.")
        Difficulty difficulty,

        @Size(max = 2000,
                message = "Os requisitos devem conter no máximo 2000 caracteres.")
        String requirements,

        @NotNull(message = "Informe a data limite do desafio.")
        @Future(message = "A data limite deve ser uma data futura.")
        LocalDate deadline

) {
}
