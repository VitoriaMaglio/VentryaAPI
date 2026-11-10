package com.ventrya.api.mapper;

import com.ventrya.api.dto.response.SubmissionResponseDTO;
import com.ventrya.api.entity.Submission;
import org.springframework.stereotype.Component;

@Component
public class SubmissionMapper {

    // Converter entidade em DTO
    public SubmissionResponseDTO toResponse(
            Submission submission
    ) {

        return new SubmissionResponseDTO(
                submission.getId(),
                submission.getApplication().getId(),
                submission.getDescription(),
                submission.getFileUrl(),
                submission.getStatus(),
                submission.getSubmittedAt()
        );
    }
}
