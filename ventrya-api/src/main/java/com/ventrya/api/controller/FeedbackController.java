package com.ventrya.api.controller;

import com.ventrya.api.dto.response.FeedbackResponseDTO;
import com.ventrya.api.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

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