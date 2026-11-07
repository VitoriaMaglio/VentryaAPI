package com.ventrya.api.dto.response;

import com.ventrya.api.entity.enums.*;

public record LoginResponseDTO(
        Long id,
        String name,
        String email,
        ProfessionalArea professionalArea,
        Role role
) {
}
