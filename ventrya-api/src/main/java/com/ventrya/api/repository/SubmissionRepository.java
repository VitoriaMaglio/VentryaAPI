package com.ventrya.api.repository;

import com.ventrya.api.entity.Application;
import com.ventrya.api.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    boolean existsByApplicationId(Long applicationId);

    Optional<Submission> findByApplicationId(Long applicationId);



}
