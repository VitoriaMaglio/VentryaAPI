package com.ventrya.api.dto.response;

import com.ventrya.api.entity.enums.ProfessionalArea;
import com.ventrya.api.entity.enums.Role;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        ProfessionalArea professionalArea,
        Role role,
        LocalDateTime createdAt
) {
}
