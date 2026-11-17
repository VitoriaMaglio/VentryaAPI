package com.ventrya.api.controller;

import com.ventrya.api.dto.request.InterviewAnswerRequestDTO;
import com.ventrya.api.dto.request.InterviewStartRequestDTO;
import com.ventrya.api.dto.response.InterviewResponseDTO;
import com.ventrya.api.service.InterviewService;
import jakarta.validation.Valid;
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

@Tag(name = "Entrevistas simuladas", description = "Simulação de entrevista e avaliação assíncrona por IA.")
@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @Operation(summary = "Iniciar entrevista", description = "Cria uma entrevista simulada com cinco perguntas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados ou parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping
    public ResponseEntity<InterviewResponseDTO> startInterview(
            @Valid @RequestBody InterviewStartRequestDTO request,
            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(interviewService.startInterview(
                        request,
                        authentication.getName()
                ));
    }

    @Operation(summary = "Minhas entrevistas", description = "Lista entrevistas do estudante autenticado com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/me")
    public ResponseEntity<Page<InterviewResponseDTO>> findMyInterviews(
            Authentication authentication,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                interviewService.findMyInterviews(
                        authentication.getName(),
                        pageable
                )
        );
    }

    @Operation(summary = "Consultar entrevista", description = "Consulta uma entrevista do estudante autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponseDTO> findById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                interviewService.findById(
                        id,
                        authentication.getName()
                )
        );
    }

    @Operation(summary = "Responder pergunta", description = "Registra a resposta da pergunta indicada na sequência exigida; ao concluir, inicia avaliação assíncrona.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados ou parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito com regra de negócio")
    })
    @PostMapping("/{interviewId}/questions/{questionNumber}/answer")
    public ResponseEntity<InterviewResponseDTO> submitAnswer(
            @PathVariable Long interviewId,
            @PathVariable Integer questionNumber,
            @Valid @RequestBody InterviewAnswerRequestDTO request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                interviewService.submitAnswer(
                        interviewId,
                        questionNumber,
                        request,
                        authentication.getName()
                )
        );
    }
}
