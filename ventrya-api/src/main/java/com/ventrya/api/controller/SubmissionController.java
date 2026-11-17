package com.ventrya.api.controller;

import com.ventrya.api.dto.request.SubmissionRequestDTO;
import com.ventrya.api.dto.response.SubmissionResponseDTO;
import com.ventrya.api.service.SubmissionService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Submissões", description = "Envio definitivo de soluções e consulta de entregas.")
@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @Operation(summary = "Enviar solução", description = "Envia uma solução única para uma candidatura; aciona processamento assíncrono de feedback.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados ou parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito com regra de negócio")
    })
    @PostMapping
    public ResponseEntity<SubmissionResponseDTO> submit(
            @Valid @RequestBody SubmissionRequestDTO request,
            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(submissionService.submit(
                        request,
                        authentication.getName()
                ));
    }

    @Operation(summary = "Consultar submissão", description = "Consulta uma submissão pertencente ao estudante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponseDTO> findById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                submissionService.findById(
                        id,
                        authentication.getName()
                )
        );
    }

    @Operation(summary = "Submissão por candidatura", description = "Consulta a submissão associada a uma candidatura.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<SubmissionResponseDTO> findByApplication(
            @PathVariable Long applicationId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                submissionService.findByApplication(
                        applicationId,
                        authentication.getName()
                )
        );
    }
}
