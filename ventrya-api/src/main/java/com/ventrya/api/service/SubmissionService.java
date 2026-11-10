package com.ventrya.api.service;

import com.ventrya.api.dto.request.SubmissionRequestDTO;
import com.ventrya.api.dto.response.SubmissionResponseDTO;
import com.ventrya.api.entity.Application;
import com.ventrya.api.entity.Submission;
import com.ventrya.api.entity.User;
import com.ventrya.api.entity.enums.Role;
import com.ventrya.api.entity.enums.Status;
import com.ventrya.api.entity.enums.SubmissionStatus;
import com.ventrya.api.mapper.SubmissionMapper;
import com.ventrya.api.repository.ApplicationRepository;
import com.ventrya.api.repository.SubmissionRepository;
import com.ventrya.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final SubmissionMapper submissionMapper;

    // Enviar solução de um desafio
    @Transactional
    public SubmissionResponseDTO submit(
            SubmissionRequestDTO request,
            String studentEmail
    ) {

        User student = findStudentByEmail(studentEmail);

        Application application = applicationRepository
                .findById(request.applicationId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Candidatura não encontrada."
                ));

        // Verificar se a candidatura pertence ao estudante
        if (!application.getStudents().getId()
                .equals(student.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Candidatura não encontrada."
            );
        }

        // Verificar se a candidatura está aprovada
        if (application.getStatus() != Status.APPROVED) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A candidatura não está aprovada."
            );
        }

        // Impedir mais de uma entrega por candidatura
        if (submissionRepository.existsByApplicationId(
                application.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe uma entrega para esta candidatura."
            );
        }

        if (application.getChallenge().getDeadLine()
                .before(new Date())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O prazo deste desafio já terminou. Não é possível enviar soluções."
            );
        }

        Submission submission = new Submission();

        submission.setApplication(application);
        submission.setDescription(request.description());
        submission.setFileUrl(request.fileUrl());
        submission.setStatus(SubmissionStatus.SUBMITTED);
        submission.setSubmittedAt(LocalDateTime.now());

        try {
            Submission saved = submissionRepository
                    .saveAndFlush(submission);

            return submissionMapper.toResponse(saved);

        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não foi possível registrar a entrega.",
                    exception
            );
        }
    }

    // Buscar entrega por ID
    @Transactional(readOnly = true)
    public SubmissionResponseDTO findById(
            Long id,
            String studentEmail
    ) {

        User student = findStudentByEmail(studentEmail);

        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entrega não encontrada."
                ));

        validateOwnership(submission, student);

        return submissionMapper.toResponse(submission);
    }

    // Buscar entrega vinculada a uma candidatura
    @Transactional(readOnly = true)
    public SubmissionResponseDTO findByApplication(
            Long applicationId,
            String studentEmail
    ) {

        User student = findStudentByEmail(studentEmail);

        Submission submission = submissionRepository
                .findByApplicationId(applicationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entrega não encontrada."
                ));

        validateOwnership(submission, student);

        return submissionMapper.toResponse(submission);
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
                    "Apenas estudantes podem acessar entregas."
            );
        }

        return student;
    }

    // Impedir acesso à entrega de outro estudante
    private void validateOwnership(
            Submission submission,
            User student
    ) {

        Long ownerId = submission.getApplication()
                .getStudents().getId();

        if (!ownerId.equals(student.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Entrega não encontrada."
            );
        }
    }


}
