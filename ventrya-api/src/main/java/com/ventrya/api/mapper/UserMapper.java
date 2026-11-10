package com.ventrya.api.mapper;

import com.ventrya.api.dto.request.RegisterRequestDTO;
import com.ventrya.api.dto.response.UserResponseDTO;
import com.ventrya.api.entity.User;
import org.springframework.stereotype.Component;

@Component//informa ao spring que essa classe deve ser gerenciada pelo container de injeção de dependencias
public class UserMapper {
//Mapper é uma classe responsável por converter dados de um obj para outro -> convete entity para dto e dto para entity//eg
    //eg temos uma entidade q é usada pelo repository mas para devolver dados para o frotn usamos o dto
    //para isso precisamos converter a entidade em mapper para acessar o -> dto
//fazemos isso quando existe uma conversão útil
    public UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfessionalArea(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    //para converter um DtoRequest para entity
    //quando o front envia dados para o banco
    /*
DTO define quais dados entram ou saem da API. Mapper realiza a conversão. Service define as regras de negócio. Repository acessa o banco.

    public User toEntity(RegisterRequestDTO requestDTO){
        User user = new User();
        user.setName(requestDTO.name());
        return user;
    }
    */

}
