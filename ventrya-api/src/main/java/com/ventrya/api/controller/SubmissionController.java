package com.ventrya.api.controller;

import com.ventrya.api.dto.request.SubmissionRequestDTO;
import com.ventrya.api.dto.response.SubmissionResponseDTO;
import com.ventrya.api.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

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
