package com.ventrya.api.service;

import com.ventrya.api.dto.response.OrganizationResponseDTO;
import com.ventrya.api.entity.Organization;
import com.ventrya.api.mapper.OrganizationMapper;
import com.ventrya.api.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import com.ventrya.api.dto.request.OrganizationRequestDTO;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    //método de get por id
    @Transactional(readOnly = true)
    public OrganizationResponseDTO findById(Long id){
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Organização não encontrada.")
                );
                return organizationMapper.toResponse(organization);

    };
    //método com paginação -> olhar os métodos do repository
    @Transactional(readOnly = true)//informa ao spring q o método deve executar isso dentro de uma transação
    //readOnly informa q á apenas leitura
    public Page<OrganizationResponseDTO> findAll(Pageable pageable){//retorna uma página de organizações convertidas em DTO  e recebe as configs da paginação

        return organizationRepository.findAll(pageable).map(organizationMapper::toResponse);
// :; Method Reference usar um método já existente sem precisar escrever uma exppressão lambda
        //para cada organização retornada execute toResponse
    };


    @Transactional
    public OrganizationResponseDTO create(OrganizationRequestDTO request) {

        if (organizationRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe uma organização com este e-mail."
            );
        }

        Organization organization = new Organization();
        organization.setName(request.name());
        organization.setEmail(request.email());
        organization.setDescription(request.description());
        organization.setWebSite(request.webSite());
        organization.setCreatedAt(LocalDateTime.now());

        Organization saved = organizationRepository.save(organization);

        return organizationMapper.toResponse(saved);
    }

    @Transactional
    public OrganizationResponseDTO update(
            Long id,
            OrganizationRequestDTO request
    ) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Organização não encontrada."
                ));

        if (!organization.getEmail().equals(request.email())
                && organizationRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe uma organização com este e-mail."
            );
        }

        organization.setName(request.name());
        organization.setEmail(request.email());
        organization.setDescription(request.description());
        organization.setWebSite(request.webSite());

        return organizationMapper.toResponse(
                organizationRepository.save(organization)
        );
    }

    @Transactional
    public void delete(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Organização não encontrada."
                ));

        if (!organization.getChallenges().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir uma organização com desafios vinculados."
            );
        }

        organization.getRepresentatives().clear();
        organizationRepository.flush();
        organizationRepository.delete(organization);
        organizationRepository.flush();
    }

}
