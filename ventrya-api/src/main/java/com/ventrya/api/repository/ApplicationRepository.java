package com.ventrya.api.repository;

import com.ventrya.api.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByStudents_IdAndChallenge_Id(//impede candidaturas duplicadas
            Long studentId,
            Long challengeId
    );

    Page<Application> findByStudents_Id(
            Long studentId,
            Pageable pageable
    );

    Page<Application> findByChallenge_Id(
            Long challengeId,
            Pageable pageable
    );


}
