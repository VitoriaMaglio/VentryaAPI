package com.ventrya.api.controller;


import com.ventrya.api.dto.response.UserResponseDTO;
import com.ventrya.api.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Administração", description = "Consulta administrativa de usuários.")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @Operation(summary = "Listar usuários", description = "Lista todos os usuários com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDTO>> findAllUsers(
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                userService.findAll(pageable)
        );
    }
}