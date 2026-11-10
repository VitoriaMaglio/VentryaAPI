package com.ventrya.api.service;

import com.ventrya.api.dto.response.ApplicationResponseDTO;
import com.ventrya.api.entity.Application;
import com.ventrya.api.entity.Challenge;
import com.ventrya.api.entity.User;
import com.ventrya.api.entity.enums.ChallengeStatus;
import com.ventrya.api.entity.enums.Role;
import com.ventrya.api.entity.enums.Status;
import com.ventrya.api.repository.ApplicationRepository;
import com.ventrya.api.repository.ChallengeRepository;
import com.ventrya.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class ApplicationService {


    //Um estudante autenticado encontra um desafio e clica em "Candidatar-se".
    //método apply precisa identificar user, buscar desafio, verificar se o estudante ja se candidatou,criar uma application vinculada, definir status inicial e data, salvar e retorn

// Criar candidatura
// apply(Long challengeId, String studentEmail)

    private final ApplicationRepository applicationRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApplicationResponseDTO apply(
        Long challengeId,
        String studentEmail) {


        User student = findStudentByEmail(studentEmail);

    Challenge challenge = challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Desafio não encontrado."
            ));

    // Verificar se o desafio está aberto
    if (challenge.getStatus() != ChallengeStatus.OPEN) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Este desafio não está disponível para candidaturas."
        );
    }

    // Verificar se o prazo de candidatura terminou
    if (challenge.getDeadLine().before(new Date())) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "O prazo deste desafio já terminou."
        );
    }

    // Impedir candidaturas duplicadas
    boolean alreadyApplied =
            applicationRepository.existsByStudents_IdAndChallenge_Id(
                    student.getId(),
                    challengeId
            );

    if (alreadyApplied) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Você já se candidatou a este desafio."
        );
    }

    // Criar candidatura
    Application application = new Application();

    application.setStudents(student);
    application.setChallenge(challenge);
    application.setStatus(Status.APPROVED);
    application.setAppliedAt(LocalDateTime.now());

    // Salvar no banco
    try {
        Application savedApplication =
                applicationRepository.saveAndFlush(application);

        return toResponse(savedApplication);

    } catch (DataIntegrityViolationException exception) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Não foi possível criar a candidatura. Verifique se ela já existe.",
                exception
        );
    }
}


// Consultar candidatura por ID
// findById(Long id, String studentEmail)
@Transactional(readOnly = true)
public ApplicationResponseDTO findById(
        Long id,
        String studentEmail
) {
    User student = findStudentByEmail(studentEmail);

    Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Candidatura não encontrada."
            ));

    if (!application.getStudents().getId().equals(student.getId())) {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Candidatura não encontrada."
        );
    }

    return toResponse(application);
}

// Listar candidaturas do estudante autenticado
// findMyApplications(String studentEmail, Pageable pageable)
    @Transactional(readOnly = true)
    public Page<ApplicationResponseDTO> findMyApplications(
        String studentEmail,
        Pageable pageable
    ) {

    User student = findStudentByEmail(studentEmail);

    return applicationRepository
            .findByStudents_Id(student.getId(), pageable)
            .map(this::toResponse);
    }
    private User findStudentByEmail(String email) {

    User student = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário autenticado não encontrado."
            ));

    if (student.getRole() != Role.STUDENT) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Apenas estudantes podem acessar candidaturas."
        );
    }

    return student;
    }

    private ApplicationResponseDTO toResponse(Application application) {

        return new ApplicationResponseDTO(
                application.getId(),
                application.getStudents().getId(),
                application.getStudents().getName(),
                application.getChallenge().getId(),
                application.getChallenge().getTitle(),
                application.getStatus(),
                application.getAppliedAt()
        );
    }
}