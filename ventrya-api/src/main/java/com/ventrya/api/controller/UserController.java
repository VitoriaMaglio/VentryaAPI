package com.ventrya.api.controller;

import com.ventrya.api.dto.response.UserResponseDTO;
import com.ventrya.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuários", description = "Consulta do perfil do usuário autenticado.")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Meu perfil", description = "Retorna o perfil do usuário autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida")
    })
    @GetMapping("/me")
    public UserResponseDTO getMyProfile(
            Authentication authentication
    ) {
        return userService.getMyProfile(
                authentication.getName()
        );
    }
}
