package com.ventrya.api.repository;


import com.ventrya.api.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findByInterview_IdOrderByQuestionNumberAsc(
            Long interviewId
    );

    Optional<InterviewQuestion> findByInterview_IdAndQuestionNumber(
            Long interviewId,
            Integer questionNumber
    );
}
