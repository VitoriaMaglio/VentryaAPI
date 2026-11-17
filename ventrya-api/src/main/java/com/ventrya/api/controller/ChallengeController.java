package com.ventrya.api.controller;

import com.ventrya.api.dto.response.ChallengeResponseDTO;
import com.ventrya.api.entity.enums.ProfessionalArea;
import com.ventrya.api.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ventrya.api.dto.request.ChallengeRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Desafios", description = "Catálogo e gerenciamento administrativo de desafios.")
@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @Operation(summary = "Listar desafios", description = "Lista desafios com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida")
    })
    @GetMapping
    public ResponseEntity<Page<ChallengeResponseDTO>> findAll(
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                challengeService.findAll(pageable)
        );
    }

    @Operation(summary = "Consultar desafio", description = "Consulta um desafio pelo identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponseDTO> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                challengeService.findById(id)
        );
    }

    @Operation(summary = "Filtrar desafios por área", description = "Lista desafios por área profissional com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados ou parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida")
    })
    @GetMapping("/area/{professionalArea}")
    public ResponseEntity<Page<ChallengeResponseDTO>> findByProfessionalArea(
            @PathVariable ProfessionalArea professionalArea,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                challengeService.findByProfessionalArea(
                        professionalArea,
                        pageable
                )
        );
    }

    @Operation(summary = "Filtrar desafios por organização", description = "Lista desafios de uma organização com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida")
    })
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<Page<ChallengeResponseDTO>> findByOrganization(
            @PathVariable Long organizationId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                challengeService.findByOrganization(
                        organizationId,
                        pageable
                )
        );
    }

    @Operation(summary = "Criar desafio", description = "ADMIN cadastra um desafio vinculado a uma organização.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Desafio criado"), @ApiResponse(responseCode = "400", description = "Dados inválidos"), @ApiResponse(responseCode = "403", description = "Acesso negado"), @ApiResponse(responseCode = "404", description = "Organização não encontrada")})
    @PostMapping("/organization/{organizationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChallengeResponseDTO> create(@PathVariable Long organizationId, @Valid @RequestBody ChallengeRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(challengeService.create(organizationId, request));
    }

    @Operation(summary = "Atualizar desafio", description = "ADMIN atualiza os dados de um desafio existente.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Desafio atualizado"), @ApiResponse(responseCode = "400", description = "Dados inválidos"), @ApiResponse(responseCode = "403", description = "Acesso negado"), @ApiResponse(responseCode = "404", description = "Desafio não encontrado")})
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChallengeResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ChallengeRequestDTO request) {
        return ResponseEntity.ok(challengeService.update(id, request));
    }

    @Operation(summary = "Excluir desafio", description = "ADMIN exclui um desafio sem candidaturas vinculadas.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Desafio excluído"), @ApiResponse(responseCode = "403", description = "Acesso negado"), @ApiResponse(responseCode = "404", description = "Desafio não encontrado"), @ApiResponse(responseCode = "409", description = "Desafio com candidaturas vinculadas")})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        challengeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
