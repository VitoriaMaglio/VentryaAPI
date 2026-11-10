package com.ventrya.api.service;


import com.ventrya.api.dto.response.PortfolioResponseDTO;
import com.ventrya.api.entity.Portfolio;
import com.ventrya.api.entity.User;
import com.ventrya.api.entity.enums.Role;
import com.ventrya.api.mapper.PortfolioMapper;
import com.ventrya.api.repository.PortfolioRepository;
import com.ventrya.api.repository.UserRepository;
import com.ventrya.api.dto.response.PortfolioAIResponseDTO;
import com.ventrya.api.entity.Submission;
import com.ventrya.api.entity.enums.PortfolioStatus;
import com.ventrya.api.repository.SubmissionRepository;
import com.ventrya.api.repository.FeedbackRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final PortfolioMapper portfolioMapper;
    private final SubmissionRepository submissionRepository;
    private final FeedbackRepository feedbackRepository;

    // Buscar portfólio pelo ID
    @Transactional(readOnly = true)
    public PortfolioResponseDTO findById(
            Long id,
            String studentEmail
    ) {

        User student = findStudentByEmail(studentEmail);

        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Portfólio não encontrado."
                ));

        validateOwnership(portfolio, student);

        return portfolioMapper.toResponse(portfolio);
    }

    // Listar portfólios do estudante autenticado
    @Transactional(readOnly = true)
    public Page<PortfolioResponseDTO> findMyPortfolios(
            String studentEmail,
            Pageable pageable
    ) {

        User student = findStudentByEmail(studentEmail);

        return portfolioRepository
                .findByStudent_Id(student.getId(), pageable)
                .map(portfolioMapper::toResponse);
    }

    // Buscar portfólio vinculado a uma entrega
    @Transactional(readOnly = true)
    public PortfolioResponseDTO findBySubmission(
            Long submissionId,
            String studentEmail
    ) {

        User student = findStudentByEmail(studentEmail);

        Portfolio portfolio = portfolioRepository
                .findBySubmission_Id(submissionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Portfólio não encontrado."
                ));

        validateOwnership(portfolio, student);

        return portfolioMapper.toResponse(portfolio);
    }

    // Buscar e validar o estudante
    private User findStudentByEmail(String email) {

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Usuário autenticado não encontrado."
                ));

        if (student.getRole() != Role.STUDENT) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Apenas estudantes podem acessar portfólios."
            );
        }

        return student;
    }

    // Verificar se o portfólio pertence ao estudante
    private void validateOwnership(
            Portfolio portfolio,
            User student
    ) {

        if (!portfolio.getStudent().getId()
                .equals(student.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Portfólio não encontrado."
            );
        }
    }

    @Transactional
    public PortfolioResponseDTO saveGeneratedPortfolio(
            Long submissionId,
            PortfolioAIResponseDTO aiResponse
    ) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entrega não encontrada."
                ));

        if (!feedbackRepository.existsBySubmission_Id(submissionId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A entrega ainda não possui feedback."
            );
        }

        if (portfolioRepository.findBySubmission_Id(submissionId).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta entrega já possui um portfólio."
            );
        }

        if (aiResponse == null
                || aiResponse.title() == null
                || aiResponse.title().isBlank()
                || aiResponse.title().length() > 255
                || aiResponse.generatedDescription() == null
                || aiResponse.generatedDescription().isBlank()
                || aiResponse.generatedDescription().length() > 4000
                || aiResponse.skills() == null
                || aiResponse.skills().isBlank()
                || aiResponse.skills().length() > 2000) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados do portfólio gerado são inválidos."
            );
        }

        Portfolio portfolio = new Portfolio();

        portfolio.setStudent(submission.getApplication().getStudents());
        portfolio.setSubmission(submission);
        portfolio.setTitle(aiResponse.title());
        portfolio.setGeneratedDescription(aiResponse.generatedDescription());
        portfolio.setSkills(aiResponse.skills());
        portfolio.setStatus(PortfolioStatus.READY);
        portfolio.setCreatedAt(LocalDateTime.now());

        Portfolio saved = portfolioRepository.saveAndFlush(portfolio);

        return portfolioMapper.toResponse(saved);
    }
}
