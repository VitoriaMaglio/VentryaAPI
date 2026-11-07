package com.ventrya.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OrganizationRequestDTO(

        @NotBlank(message = "O nome da organização é obrigatório.")
        @Size(min = 3, max = 255,
                message = "O nome deve conter entre 3 e 255 caracteres.")
        String name,
        
        @NotBlank(message = "O e-mail da organização é obrigatório.")
        @Email(message = "Informe um e-mail válido para a organização.")
        @Size(max = 255,
                message = "O e-mail deve conter no máximo 255 caracteres.")
        String email,

        @NotBlank(message = "A descrição da organização é obrigatória.")
        @Size(max = 2000,
                message = "A descrição deve conter no máximo 2000 caracteres.")
        String description,

        @Size(max = 255,
                message = "O endereço do site deve conter no máximo 255 caracteres.")
        @Pattern(
                regexp = "^(https?://.+)?$",
                message = "Informe uma URL iniciada por http:// ou https://."
        )
        String webSite
) {
}
