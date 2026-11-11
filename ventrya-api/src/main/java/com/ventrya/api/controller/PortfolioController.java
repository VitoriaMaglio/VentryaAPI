package com.ventrya.api.controller;

import com.ventrya.api.dto.response.PortfolioResponseDTO;
import com.ventrya.api.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/me")
    public ResponseEntity<Page<PortfolioResponseDTO>> findMyPortfolios(
            Authentication authentication,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                portfolioService.findMyPortfolios(
                        authentication.getName(),
                        pageable
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioResponseDTO> findById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                portfolioService.findById(
                        id,
                        authentication.getName()
                )
        );
    }

    @GetMapping("/submission/{submissionId}")
    public ResponseEntity<PortfolioResponseDTO> findBySubmission(
            @PathVariable Long submissionId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                portfolioService.findBySubmission(
                        submissionId,
                        authentication.getName()
                )
        );
    }
}
