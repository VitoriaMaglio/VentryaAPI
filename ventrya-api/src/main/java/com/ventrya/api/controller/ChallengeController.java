package com.ventrya.api.controller;

import com.ventrya.api.dto.response.ChallengeResponseDTO;
import com.ventrya.api.entity.enums.ProfessionalArea;
import com.ventrya.api.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
