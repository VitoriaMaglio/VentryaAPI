package com.ventrya.api.dto.request;

import com.ventrya.api.entity.enums.ProfessionalArea;
import jakarta.validation.constraints.NotNull;

public record InterviewRequestDTO(

        @NotNull(message = "Selecione uma área profissional para a entrevista.")
        ProfessionalArea professionalArea
) {
}
