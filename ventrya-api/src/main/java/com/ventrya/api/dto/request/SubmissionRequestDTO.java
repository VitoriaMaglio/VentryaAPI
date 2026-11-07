package com.ventrya.api.dto.request;

import jakarta.validation.constraints.*;

public record SubmissionRequestDTO(

        @NotNull(message = "A inscrição é obrigatória.")
        @Positive(message = "O ID da inscrição deve ser positivo.")
        Long applicationId,

        @NotBlank(message = "Descreva a solução desenvolvida.")
        @Size(min = 20, max = 2000,
                message = "A descrição deve conter entre 20 e 2000 caracteres.")
        String description,

        @Size(max = 255,
                message = "O link do arquivo deve conter no máximo 255 caracteres.")
        @Pattern(
                regexp = "^(https?://.+)?$",
                message = "Informe um link válido iniciado por http:// ou https://."
        )
        String fileUrl
){
}
