package com.ventrya.api.mapper;

import com.ventrya.api.dto.response.InterviewQuestionResponseDTO;
import com.ventrya.api.dto.response.InterviewResponseDTO;
import com.ventrya.api.entity.Interview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InterviewMapper {

    private final InterviewQuestionMapper interviewQuestionMapper;

    public InterviewResponseDTO toResponse(Interview interview) {

        List<InterviewQuestionResponseDTO> questions =
                interview.getQuestions()
                        .stream()
                        .map(interviewQuestionMapper::toResponse)
                        .toList();

        return new InterviewResponseDTO(
                interview.getId(),
                interview.getStudent().getId(),
                interview.getProfessionalArea(),
                interview.getStatus(),
                interview.getOverallFeedback(),
                interview.getCreatedAt(),
                interview.getCompletedAt(),
                questions
        );
    }
}
