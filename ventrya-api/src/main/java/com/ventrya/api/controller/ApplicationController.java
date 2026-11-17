package com.ventrya.api.controller;

import com.ventrya.api.dto.response.ApplicationResponseDTO;
import com.ventrya.api.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Candidaturas", description = "Candidaturas dos estudantes aos desafios.")
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "Candidatar-se a desafio", description = "Registra uma candidatura única para um desafio aberto e dentro do prazo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados ou parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito com regra de negócio")
    })
    @PostMapping("/challenge/{challengeId}")
    public ResponseEntity<ApplicationResponseDTO> apply(
            @PathVariable Long challengeId,
            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationService.apply(
                        challengeId,
                        authentication.getName()
                ));
    }

    @Operation(summary = "Minhas candidaturas", description = "Lista as candidaturas do estudante autenticado com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/me")
    public ResponseEntity<Page<ApplicationResponseDTO>> findMyApplications(
            Authentication authentication,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                applicationService.findMyApplications(
                        authentication.getName(),
                        pageable
                )
        );
    }

    @Operation(summary = "Consultar candidatura", description = "Consulta uma candidatura pertencente ao estudante autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> findById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                applicationService.findById(
                        id,
                        authentication.getName()
                )
        );
    }
}
