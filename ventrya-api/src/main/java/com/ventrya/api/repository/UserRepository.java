package com.ventrya.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ventrya.api.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);//impede cadastro de emails duplicados
//usamos Optional pois esse é um registro que pode ou não existir
    Optional<User> findByEmail(String email);//localiza o user durante a autenticação
}
