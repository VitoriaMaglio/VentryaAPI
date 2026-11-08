package com.ventrya.api.repository;

import com.ventrya.api.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByStudentsIdAndChallengeId(//impede candidaturas duplicadas
            Long studentId,
            Long challengeId
    );

    Page<Application> findByStudentsId(
            Long studentId,
            Pageable pageable
    );

    Page<Application> findByChallengeId(
            Long challengeId,
            Pageable pageable
    );


}
