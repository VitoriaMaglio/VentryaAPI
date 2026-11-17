package com.ventrya.api.controller;

import com.ventrya.api.dto.response.OrganizationResponseDTO;
import com.ventrya.api.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ventrya.api.dto.request.OrganizationRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Organizações", description = "Consulta e gerenciamento administrativo de organizações parceiras.")
@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @Operation(summary = "Listar organizações", description = "Lista organizações com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida")
    })
    @GetMapping
    public ResponseEntity<Page<OrganizationResponseDTO>> findAll(
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                organizationService.findAll(pageable)
        );
    }

    @Operation(summary = "Consultar organização", description = "Consulta uma organização pelo identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou inválida"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponseDTO> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                organizationService.findById(id)
        );
    }

    @Operation(summary = "Criar organização", description = "ADMIN cadastra uma organização com e-mail único.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Organização criada"), @ApiResponse(responseCode = "400", description = "Dados inválidos"), @ApiResponse(responseCode = "403", description = "Acesso negado"), @ApiResponse(responseCode = "409", description = "E-mail duplicado")})
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrganizationResponseDTO> create(@Valid @RequestBody OrganizationRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.create(request));
    }

    @Operation(summary = "Atualizar organização", description = "ADMIN atualiza uma organização existente.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Organização atualizada"), @ApiResponse(responseCode = "400", description = "Dados inválidos"), @ApiResponse(responseCode = "403", description = "Acesso negado"), @ApiResponse(responseCode = "404", description = "Organização não encontrada"), @ApiResponse(responseCode = "409", description = "E-mail duplicado")})
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrganizationResponseDTO> update(@PathVariable Long id, @Valid @RequestBody OrganizationRequestDTO request) {
        return ResponseEntity.ok(organizationService.update(id, request));
    }

    @Operation(summary = "Excluir organização", description = "ADMIN exclui uma organização sem desafios vinculados.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Organização excluída"), @ApiResponse(responseCode = "403", description = "Acesso negado"), @ApiResponse(responseCode = "404", description = "Organização não encontrada"), @ApiResponse(responseCode = "409", description = "Organização com desafios vinculados")})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        organizationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
