package com.ventrya.api.service;

import com.ventrya.api.dto.request.InterviewStartRequestDTO;
import com.ventrya.api.dto.request.InterviewAnswerRequestDTO;
import com.ventrya.api.dto.response.InterviewResponseDTO;
import com.ventrya.api.entity.Interview;
import com.ventrya.api.entity.InterviewQuestion;
import com.ventrya.api.entity.User;
import com.ventrya.api.entity.enums.InterviewStatus;
import com.ventrya.api.entity.enums.Role;
import com.ventrya.api.mapper.InterviewMapper;
import com.ventrya.api.repository.InterviewRepository;
import com.ventrya.api.repository.InterviewQuestionRepository;
import com.ventrya.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final UserRepository userRepository;
    private final InterviewMapper interviewMapper;

    private static final List<String> QUESTIONS = List.of(
            "Fale um pouco sobre você.",
            "Quais são seus principais pontos fortes e o que você precisa melhorar?",
            "Por que você tem interesse nesta oportunidade ou área profissional?",
            "Conte sobre um desafio que enfrentou e como resolveu.",
            "Como você lida com trabalho em equipe e situações de conflito?"
    );

    // Iniciar uma entrevista com cinco perguntas
    @Transactional
    public InterviewResponseDTO startInterview(
            InterviewStartRequestDTO request,
            String studentEmail
    ) {

        User student = findStudentByEmail(studentEmail);

        if (request.professionalArea() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A área profissional é obrigatória."
            );
        }

        Interview interview = new Interview();

        interview.setStudent(student);
        interview.setProfessionalArea(request.professionalArea());
        interview.setStatus(InterviewStatus.IN_PROGRESS);
        interview.setCreatedAt(LocalDateTime.now());

        // Criar as cinco perguntas da sessão
        for (int i = 0; i < QUESTIONS.size(); i++) {

            InterviewQuestion question = new InterviewQuestion();

            question.setInterview(interview);
            question.setQuestionNumber(i + 1);
            question.setQuestion(QUESTIONS.get(i));

            interview.getQuestions().add(question);
        }

        Interview saved = interviewRepository.saveAndFlush(interview);

        return interviewMapper.toResponse(saved);
    }

    // Buscar entrevista pelo ID
    @Transactional(readOnly = true)
    public InterviewResponseDTO findById(
            Long id,
            String studentEmail
    ) {

        User student = findStudentByEmail(studentEmail);

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entrevista não encontrada."
                ));

        validateOwnership(interview, student);

        return interviewMapper.toResponse(interview);
    }

    // Listar entrevistas do estudante
    @Transactional(readOnly = true)
    public Page<InterviewResponseDTO> findMyInterviews(
            String studentEmail,
            Pageable pageable
    ) {

        User student = findStudentByEmail(studentEmail);

        return interviewRepository
                .findByStudent_Id(student.getId(), pageable)
                .map(interviewMapper::toResponse);
    }

    // Responder a uma pergunta específica
    @Transactional
    public InterviewResponseDTO submitAnswer(
            Long interviewId,
            Integer questionNumber,
            InterviewAnswerRequestDTO request,
            String studentEmail
    ) {

        User student = findStudentByEmail(studentEmail);

        // Bloquear alterações simultâneas na mesma entrevista
        Interview interview = interviewRepository
                .findLockedById(interviewId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entrevista não encontrada."
                ));

        validateOwnership(interview, student);

        // Somente entrevistas em andamento podem receber respostas
        if (interview.getStatus() != InterviewStatus.IN_PROGRESS) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta entrevista não permite novas respostas."
            );
        }

        if (request.answer() == null
                || request.answer().isBlank()
                || request.answer().length() > 4000) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A resposta deve conter entre 1 e 4000 caracteres."
            );
        }

        // Buscar a pergunta pelo número

        if (questionNumber == null
                || questionNumber < 1
                || questionNumber > 5) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O número da pergunta deve estar entre 1 e 5."
            );
        }
        InterviewQuestion question = interviewQuestionRepository
                .findByInterview_IdAndQuestionNumber(
                        interviewId,
                        questionNumber
                )
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pergunta não encontrada."
                ));

        // Impedir alterações em respostas já enviadas
        if (question.getAnswer() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta pergunta já foi respondida."
            );
        }

        // Garantir que as perguntas sejam respondidas em sequência
        boolean previousQuestionsAnswered = interview.getQuestions()
                .stream()
                .filter(q -> q.getQuestionNumber() < questionNumber)
                .allMatch(q -> q.getAnswer() != null);

        if (!previousQuestionsAnswered) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Responda às perguntas anteriores primeiro."
            );
        }

        question.setAnswer(request.answer());

        interviewQuestionRepository.save(question);

        // Verificar se todas as cinco perguntas foram respondidas
        boolean allAnswered = interview.getQuestions().size() == 5
                && interview.getQuestions()
                .stream()
                .allMatch(q -> q.getAnswer() != null);

        if (allAnswered) {
            interview.setStatus(InterviewStatus.PROCESSING);
        }

        Interview saved = interviewRepository.saveAndFlush(interview);

        return interviewMapper.toResponse(saved);
    }

    // Validar estudante autenticado
    private User findStudentByEmail(String email) {

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Usuário autenticado não encontrado."
                ));

        if (student.getRole() != Role.STUDENT) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Apenas estudantes podem acessar entrevistas."
            );
        }

        return student;
    }

    // Validar se a entrevista pertence ao estudante
    private void validateOwnership(
            Interview interview,
            User student
    ) {

        if (!interview.getStudent().getId()
                .equals(student.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Entrevista não encontrada."
            );
        }
    }

    @Transactional
    public InterviewResponseDTO completeInterview(
            Long interviewId,
            Map<Integer, String> questionFeedbacks,
            String overallFeedback
    ) {
        Interview interview = interviewRepository
                .findLockedById(interviewId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entrevista não encontrada."
                ));

        if (interview.getStatus() != InterviewStatus.PROCESSING) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A entrevista não está aguardando avaliação."
            );
        }

        if (questionFeedbacks == null
                || questionFeedbacks.size() != 5
                || overallFeedback == null
                || overallFeedback.isBlank()
                || overallFeedback.length() > 4000) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A avaliação da entrevista está incompleta."
            );
        }

        if (interview.getQuestions().size() != 5) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A entrevista não possui cinco perguntas."
            );
        }

        for (InterviewQuestion question : interview.getQuestions()) {

            if (question.getAnswer() == null) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Existem perguntas sem resposta."
                );
            }

            String feedback = questionFeedbacks.get(
                    question.getQuestionNumber()
            );

            if (feedback == null
                    || feedback.isBlank()
                    || feedback.length() > 4000) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O feedback da pergunta "
                                + question.getQuestionNumber()
                                + " é inválido."
                );
            }

            question.setAiFeedback(feedback);
        }

        interview.setOverallFeedback(overallFeedback);
        interview.setStatus(InterviewStatus.COMPLETED);
        interview.setCompletedAt(LocalDateTime.now());

        Interview saved = interviewRepository.saveAndFlush(interview);

        return interviewMapper.toResponse(saved);
    }
}