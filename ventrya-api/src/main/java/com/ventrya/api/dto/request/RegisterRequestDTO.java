package com.ventrya.api.dto.request;

import com.ventrya.api.entity.enums.ProfessionalArea;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record RegisterRequestDTO(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 255, message = "O nome deve conter entre 3 e 255 caracteres.")
        String name,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Informe um endereço de e-mail válido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, max = 72, message = "A senha deve conter entre 8 e 72 caracteres.")
        String password,

        @NotNull(message = "A área profissional é obrigatória.")
        ProfessionalArea professionalArea

) {}


