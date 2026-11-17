package com.ventrya.api.controller;

import com.ventrya.api.dto.response.OrganizationResponseDTO;
import com.ventrya.api.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ventrya.api.dto.request.OrganizationRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrganizationResponseDTO> create(
            @Valid @RequestBody OrganizationRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(organizationService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrganizationResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationRequestDTO request
    ) {
        return ResponseEntity.ok(
                organizationService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        organizationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
