package com.ventrya.api.repository;


import com.ventrya.api.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findBySubmission_Id(
            Long submissionId
    );

    boolean existsBySubmission_Id(Long submissionId);
//verifica se um feedback já foi gerado evitando criar outro para a mesma enrtega
}
