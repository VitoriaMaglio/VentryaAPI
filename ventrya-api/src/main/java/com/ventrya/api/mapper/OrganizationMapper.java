package com.ventrya.api.mapper;

import com.ventrya.api.dto.response.OrganizationResponseDTO;
import com.ventrya.api.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    //fluxo do sistema com organization -> não precisa de crus de create update e delete pois vai ser
    //dado fictíco
    //response para devolver informações certas para o front

    public OrganizationResponseDTO toResponse(Organization organization){
        return new OrganizationResponseDTO(
                organization.getId(),
                organization.getName(),
                organization.getEmail(),
                organization.getDescription(),
                organization.getWebSite(),
                organization.getCreatedAt()
                );
    }
}
