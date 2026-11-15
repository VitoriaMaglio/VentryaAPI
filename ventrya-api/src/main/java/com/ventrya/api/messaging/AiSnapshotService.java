package com.ventrya.api.messaging;

import com.ventrya.api.ai.AiGenerationService;
import com.ventrya.api.entity.*;
import com.ventrya.api.entity.enums.InterviewStatus;
import com.ventrya.api.repository.InterviewRepository;
import com.ventrya.api.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiSnapshotService {
    private final SubmissionRepository submissions;
    private final InterviewRepository interviews;

    @Transactional(readOnly = true)
    public SubmissionData submission(Long id) {
        Submission s = submissions.findById(id).orElseThrow();
        Challenge c = s.getApplication().getChallenge();
        return new SubmissionData(c.getTitle(), c.getRequirements(), s.getDescription());
    }

    @Transactional(readOnly = true)
    public InterviewData interview(Long id) {
        Interview i = interviews.findById(id).orElseThrow();
        List<AiGenerationService.InterviewAnswer> answers = i.getQuestions().stream()
                .map(q -> new AiGenerationService.InterviewAnswer(q.getQuestionNumber(), q.getQuestion(), q.getAnswer()))
                .toList();
        return new InterviewData(i.getStatus(), i.getProfessionalArea().name(), answers);
    }

    public record SubmissionData(String title, String requirements, String description) {}
    public record InterviewData(InterviewStatus status, String area, List<AiGenerationService.InterviewAnswer> answers) {}
}
