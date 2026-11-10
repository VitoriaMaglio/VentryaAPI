package com.ventrya.api.service;

import com.ventrya.api.dto.response.FeedbackResponseDTO;
import com.ventrya.api.entity.Feedback;
import com.ventrya.api.entity.User;
import com.ventrya.api.entity.enums.Role;
import com.ventrya.api.mapper.FeedbackMapper;
import com.ventrya.api.repository.FeedbackRepository;
import com.ventrya.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.ventrya.api.dto.response.FeedbackAIResponseDTO;
import com.ventrya.api.entity.Submission;
import com.ventrya.api.repository.SubmissionRepository;
import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final FeedbackMapper feedbackMapper;
    private final SubmissionRepository submissionRepository;

    // Buscar avaliação pelo ID
    @Transactional(readOnly = true)
    public FeedbackResponseDTO findById(
            Long id,
            String studentEmail
    ) {

        User student = findStudentByEmail(studentEmail);

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Avaliação não encontrada."
                ));

        validateOwnership(feedback, student);

        return feedbackMapper.toResponse(feedback);
    }

    // Buscar avaliação de uma entrega
    @Transactional(readOnly = true)
    public FeedbackResponseDTO findBySubmission(
            Long submissionId,
            String studentEmail
    ) {

        User student = findStudentByEmail(studentEmail);

        Feedback feedback = feedbackRepository
                .findBySubmission_Id(submissionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Avaliação não encontrada."
                ));

        validateOwnership(feedback, student);

        return feedbackMapper.toResponse(feedback);
    }

    // Buscar e validar o estudante autenticado
    private User findStudentByEmail(String email) {

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Usuário autenticado não encontrado."
                ));

        if (student.getRole() != Role.STUDENT) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Apenas estudantes podem consultar avaliações."
            );
        }

        return student;
    }

    // Verificar se o feedback pertence ao estudante
    private void validateOwnership(
            Feedback feedback,
            User student
    ) {

        Long ownerId = feedback.getSubmission()
                .getApplication()
                .getStudents()
                .getId();

        if (!ownerId.equals(student.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Avaliação não encontrada."
            );
        }
    }

    @Transactional
    public FeedbackResponseDTO saveGeneratedFeedback(
            Long submissionId,
            FeedbackAIResponseDTO aiResponse
    ) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entrega não encontrada."
                ));

        if (feedbackRepository.existsBySubmission_Id(submissionId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta entrega já possui uma avaliação."
            );
        }

        if (aiResponse == null
                || aiResponse.comment() == null
                || aiResponse.comment().isBlank()
                || aiResponse.comment().length() > 2000
                || aiResponse.rating() == null
                || aiResponse.rating() < 1
                || aiResponse.rating() > 5
                || aiResponse.strengths() == null
                || aiResponse.strengths().isBlank()
                || aiResponse.strengths().length() > 2000
                || aiResponse.improvements() == null
                || aiResponse.improvements().isBlank()
                || aiResponse.improvements().length() > 2000) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados da avaliação gerada são inválidos."
            );
        }

        Feedback feedback = new Feedback();

        feedback.setSubmission(submission);
        feedback.setComment(aiResponse.comment());
        feedback.setRating(aiResponse.rating());
        feedback.setStrengths(aiResponse.strengths());
        feedback.setImprovements(aiResponse.improvements());
        feedback.setCreatedAt(LocalDateTime.now());

        Feedback saved = feedbackRepository.saveAndFlush(feedback);

        return feedbackMapper.toResponse(saved);
    }
}
