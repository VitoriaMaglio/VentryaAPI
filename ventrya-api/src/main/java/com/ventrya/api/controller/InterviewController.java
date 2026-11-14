package com.ventrya.api.controller;

import com.ventrya.api.dto.request.InterviewAnswerRequestDTO;
import com.ventrya.api.dto.request.InterviewStartRequestDTO;
import com.ventrya.api.dto.response.InterviewResponseDTO;
import com.ventrya.api.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

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
