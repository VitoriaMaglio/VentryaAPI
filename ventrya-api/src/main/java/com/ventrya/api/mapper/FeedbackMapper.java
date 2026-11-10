package com.ventrya.api.mapper;

import com.ventrya.api.dto.response.FeedbackResponseDTO;
import com.ventrya.api.entity.Feedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public FeedbackResponseDTO toResponse(Feedback feedback) {

        return new FeedbackResponseDTO(
                feedback.getId(),
                feedback.getSubmission().getId(),
                feedback.getComment(),
                feedback.getRating(),
                feedback.getStrengths(),
                feedback.getImprovements(),
                feedback.getCreatedAt()
        );
    }
}
