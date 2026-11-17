package com.ventrya.api.service;

import com.ventrya.api.dto.response.ChallengeResponseDTO;
import com.ventrya.api.entity.Challenge;
import com.ventrya.api.entity.enums.ProfessionalArea;
import com.ventrya.api.mapper.ChallengeMapper;
import com.ventrya.api.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.ventrya.api.dto.request.ChallengeRequestDTO;
import com.ventrya.api.entity.Organization;
import com.ventrya.api.entity.enums.ChallengeStatus;
import com.ventrya.api.repository.OrganizationRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;
    private final OrganizationRepository organizationRepository;
    @Transactional(readOnly = true)
    public ChallengeResponseDTO findById(Long id) {

        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Desafio não encontrado."
                ));

        return challengeMapper.toResponse(challenge);
    }

    @Transactional(readOnly = true)
    public Page<ChallengeResponseDTO> findAll(Pageable pageable) {

        return challengeRepository.findAll(pageable)
                .map(challengeMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ChallengeResponseDTO> findByProfessionalArea(
            ProfessionalArea professionalArea,
            Pageable pageable
    ) {

        return challengeRepository
                .findByProfessionalArea(professionalArea, pageable)
                .map(challengeMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ChallengeResponseDTO> findByOrganization(
            Long organizationId,
            Pageable pageable
    ) {

        return challengeRepository
                .findByOrganization_Id(organizationId, pageable)
                .map(challengeMapper::toResponse);
    }


    @Transactional
    public ChallengeResponseDTO create(
            Long organizationId,
            ChallengeRequestDTO request
    ) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Organização não encontrada."
                ));

        Challenge challenge = new Challenge();

        challenge.setTitle(request.title());
        challenge.setDescription(request.description());
        challenge.setProfessionalArea(request.professionalArea());
        challenge.setDifficulty(request.difficulty());
        challenge.setRequirements(request.requirements());
        challenge.setDeadLine(toDate(request.deadline()));
        challenge.setStatus(ChallengeStatus.OPEN);
        challenge.setOrganization(organization);
        challenge.setCreatedAt(LocalDateTime.now());

        return challengeMapper.toResponse(
                challengeRepository.save(challenge)
        );
    }

    @Transactional
    public ChallengeResponseDTO update(
            Long id,
            ChallengeRequestDTO request
    ) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Desafio não encontrado."
                ));

        challenge.setTitle(request.title());
        challenge.setDescription(request.description());
        challenge.setProfessionalArea(request.professionalArea());
        challenge.setDifficulty(request.difficulty());
        challenge.setRequirements(request.requirements());
        challenge.setDeadLine(toDate(request.deadline()));

        return challengeMapper.toResponse(
                challengeRepository.save(challenge)
        );
    }

    @Transactional
    public void delete(Long id) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Desafio não encontrado."
                ));

        if (!challenge.getApplications().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir um desafio com candidaturas vinculadas."
            );
        }

        challengeRepository.delete(challenge);
        challengeRepository.flush();
    }

    private Date toDate(java.time.LocalDate deadline) {
        return Date.from(
                deadline.atStartOfDay(ZoneId.systemDefault()).toInstant()
        );
    }


}
