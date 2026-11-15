package com.ventrya.api.messaging;

import com.ventrya.api.ai.AiGenerationService;
import com.ventrya.api.config.RabbitMqConfig;
import com.ventrya.api.entity.*;
import com.ventrya.api.entity.enums.InterviewStatus;
import com.ventrya.api.repository.*;
import com.ventrya.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class AiEventConsumer {
    private final AiGenerationService ai;
    private final FeedbackRepository feedbacks;
    private final PortfolioRepository portfolios;
    private final FeedbackService feedbackService;
    private final PortfolioService portfolioService;
    private final InterviewService interviewService;
    private final AiSnapshotService snapshots;

    @RabbitListener(queues = RabbitMqConfig.SUBMISSION_QUEUE)
    public void onSubmissionCreated(String id) {
        Long submissionId = Long.valueOf(id);
        if (!feedbacks.existsBySubmission_Id(submissionId)) {
            var data = snapshots.submission(submissionId);
            var generated = ai.generateFeedback(data.title(), data.requirements(), data.description());
            feedbackService.saveGeneratedFeedback(submissionId, generated);
        }
        if (portfolios.findBySubmission_Id(submissionId).isEmpty()) {
            var data = snapshots.submission(submissionId);
            Feedback feedback = feedbacks.findBySubmission_Id(submissionId).orElseThrow();
            var generated = ai.generatePortfolio(data.title(), data.description(), feedback.getComment());
            portfolioService.saveGeneratedPortfolio(submissionId, generated);
        }
    }

    @RabbitListener(queues = RabbitMqConfig.INTERVIEW_QUEUE)
    public void onInterviewCompleted(String id) {
        Long interviewId = Long.valueOf(id);
        var data = snapshots.interview(interviewId);
        if (data.status() == InterviewStatus.COMPLETED) return;
        if (data.status() != InterviewStatus.PROCESSING) return;
        var result = ai.generateInterviewEvaluation(data.area(), data.answers());
        if (result == null || result.questionFeedbacks() == null || result.questionFeedbacks().size() != 5) {
            throw new IllegalStateException("A IA não retornou cinco avaliações.");
        }
        Map<Integer, String> perQuestion = new HashMap<>();
        for (var item : result.questionFeedbacks()) {
            if (item == null || item.questionNumber() < 1 || item.questionNumber() > 5
                    || perQuestion.putIfAbsent(item.questionNumber(), item.feedback()) != null) {
                throw new IllegalStateException("Avaliações duplicadas ou inválidas.");
            }
        }
        interviewService.completeInterview(interviewId, perQuestion, result.overallFeedback());
    }

}
