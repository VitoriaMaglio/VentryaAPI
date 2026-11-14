package com.ventrya.api.controller;

import com.ventrya.api.dto.response.OrganizationResponseDTO;
import com.ventrya.api.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<Page<OrganizationResponseDTO>> findAll(
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                organizationService.findAll(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponseDTO> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                organizationService.findById(id)
        );
    }
}
