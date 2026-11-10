package com.ventrya.api.mapper;

import com.ventrya.api.dto.response.PortfolioResponseDTO;
import com.ventrya.api.entity.Portfolio;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapper {

    public PortfolioResponseDTO toResponse(Portfolio portfolio) {

        return new PortfolioResponseDTO(
                portfolio.getId(),
                portfolio.getStudent().getId(),
                portfolio.getSubmission().getId(),
                portfolio.getTitle(),
                portfolio.getGeneratedDescription(),
                portfolio.getSkills(),
                portfolio.getStatus(),
                portfolio.getCreatedAt()
        );
    }
}