package com.ventrya.api.controller;

import com.ventrya.api.dto.response.FeedbackResponseDTO;
import com.ventrya.api.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Feedbacks de IA", description = "Consulta de feedbacks gerados assincronamente pela IA após submissões.")
@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Operation(summary = "Consultar feedback", description = "Consulta um feedback acessível ao estudante autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponseDTO> findById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                feedbackService.findById(
                        id,
                        authentication.getName()
                )
        );
    }

    @Operation(summary = "Feedback por submissão", description = "Consulta o feedback gerado para uma submissão; pode ainda não existir durante o processamento assíncrono.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/submission/{submissionId}")
    public ResponseEntity<FeedbackResponseDTO> findBySubmission(
            @PathVariable Long submissionId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                feedbackService.findBySubmission(
                        submissionId,
                        authentication.getName()
                )
        );
    }
}
