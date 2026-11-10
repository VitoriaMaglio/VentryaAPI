package com.ventrya.api.repository;

import com.ventrya.api.entity.Interview;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository
        extends JpaRepository<Interview, Long> {

    Page<Interview> findByStudent_Id(
            Long studentId,
            Pageable pageable
    );
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Interview i WHERE i.id = :id")
    Optional<Interview> findLockedById(@Param("id") Long id);
}

