package com.ventrya.api.controller;

import com.ventrya.api.dto.response.ChallengeResponseDTO;
import com.ventrya.api.entity.enums.ProfessionalArea;
import com.ventrya.api.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ventrya.api.dto.request.ChallengeRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping
    public ResponseEntity<Page<ChallengeResponseDTO>> findAll(
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                challengeService.findAll(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponseDTO> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                challengeService.findById(id)
        );
    }

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

    @PostMapping("/organization/{organizationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChallengeResponseDTO> create(
            @PathVariable Long organizationId,
            @Valid @RequestBody ChallengeRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(challengeService.create(organizationId, request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChallengeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ChallengeRequestDTO request
    ) {
        return ResponseEntity.ok(
                challengeService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        challengeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
