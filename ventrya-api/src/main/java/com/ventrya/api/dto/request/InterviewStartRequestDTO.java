package com.ventrya.api.dto.request;

import com.ventrya.api.entity.enums.ProfessionalArea;
import jakarta.validation.constraints.NotNull;

public record InterviewStartRequestDTO(

        @NotNull(message = "A área profissional é obrigatória.")
        ProfessionalArea professionalArea

) {
}