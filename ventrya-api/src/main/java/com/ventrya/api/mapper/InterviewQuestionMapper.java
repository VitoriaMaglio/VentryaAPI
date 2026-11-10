package com.ventrya.api.mapper;

import com.ventrya.api.dto.response.InterviewQuestionResponseDTO;
import com.ventrya.api.entity.InterviewQuestion;
import org.springframework.stereotype.Component;

@Component
public class InterviewQuestionMapper {

    public InterviewQuestionResponseDTO toResponse(
            InterviewQuestion question
    ) {

        return new InterviewQuestionResponseDTO(
                question.getId(),
                question.getQuestionNumber(),
                question.getQuestion(),
                question.getAnswer(),
                question.getAiFeedback()
        );
    }
}
