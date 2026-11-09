package com.ventrya.api.repository;

import com.ventrya.api.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Page<Portfolio> findByStudentId(
            Long studentId,
            Pageable pageable
    );

    Optional<Portfolio> findBySubmissionId(
            Long submissionId
    );

}
