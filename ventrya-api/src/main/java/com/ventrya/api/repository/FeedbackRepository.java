package com.ventrya.api.repository;


import com.ventrya.api.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findBySubmissionId(
            Long submissionId
    );

    boolean existsBySubmissionId(Long submissionId);
//verifica se um feedback já foi gerado evitando criar outro para a mesma enrtega
}
