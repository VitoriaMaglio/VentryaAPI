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

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;

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

}
