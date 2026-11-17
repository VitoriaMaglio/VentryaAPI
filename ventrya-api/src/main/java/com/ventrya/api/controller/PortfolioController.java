package com.ventrya.api.controller;

import com.ventrya.api.dto.response.PortfolioResponseDTO;
import com.ventrya.api.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Portfólios", description = "Portfólios gerados automaticamente após feedbacks de IA.")
@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Operation(summary = "Meus portfólios", description = "Lista portfólios do estudante autenticado com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/me")
    public ResponseEntity<Page<PortfolioResponseDTO>> findMyPortfolios(
            Authentication authentication,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                portfolioService.findMyPortfolios(
                        authentication.getName(),
                        pageable
                )
        );
    }

    @Operation(summary = "Consultar portfólio", description = "Consulta um portfólio acessível ao estudante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PortfolioResponseDTO> findById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                portfolioService.findById(
                        id,
                        authentication.getName()
                )
        );
    }

    @Operation(summary = "Portfólio por submissão", description = "Consulta o portfólio associado a uma submissão após processamento assíncrono.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/submission/{submissionId}")
    public ResponseEntity<PortfolioResponseDTO> findBySubmission(
            @PathVariable Long submissionId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                portfolioService.findBySubmission(
                        submissionId,
                        authentication.getName()
                )
        );
    }
}
