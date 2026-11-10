package com.ventrya.api.mapper;

import com.ventrya.api.dto.response.ChallengeResponseDTO;
import com.ventrya.api.entity.Challenge;
import com.ventrya.api.entity.Organization;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class ChallengeMapper {

    public ChallengeResponseDTO toResponse(Challenge challenge){
        Organization organization = challenge.getOrganization();

        LocalDate deadline = challenge.getDeadLine()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return new ChallengeResponseDTO(
                challenge.getId(),
                challenge.getTitle(),
                challenge.getDescription(),
                challenge.getProfessionalArea(),
                challenge.getDifficulty(),
                challenge.getRequirements(),
                deadline,
                challenge.getStatus(),
                organization != null ? organization.getId() : null,
                organization != null ? organization.getName() : null,
                challenge.getCreatedAt()
        );
    }
}
