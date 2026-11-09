package com.ventrya.api.repository;

import com.ventrya.api.entity.Interview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {


    Page<Interview> findByStudentId(
            Long studentId,
            Pageable pageable
    );



}

