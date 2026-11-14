package com.ventrya.api.controller;

import com.ventrya.api.dto.response.ApplicationResponseDTO;
import com.ventrya.api.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

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
