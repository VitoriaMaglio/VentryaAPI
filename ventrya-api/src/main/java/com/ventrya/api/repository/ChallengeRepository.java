package com.ventrya.api.repository;

import com.ventrya.api.entity.Challenge;
import com.ventrya.api.entity.enums.ProfessionalArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Page<Challenge> findByOrganization_Id(
            Long organizationId,
            Pageable pageable
    );

    Page<Challenge> findByProfessionalArea(
            ProfessionalArea professionalArea,
            Pageable pageable
    );
}
