package com.ventrya.api.repository;

import com.ventrya.api.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    boolean existsByEmail(String email);

    Page<Organization> findByRepresentativesId(
            Long userId,
            Pageable pageable
    );
}
