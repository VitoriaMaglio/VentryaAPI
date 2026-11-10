package com.ventrya.api.service;

import com.ventrya.api.dto.request.LoginRequestDTO;
import com.ventrya.api.dto.request.RegisterRequestDTO;
import com.ventrya.api.dto.response.AuthResponse;
import com.ventrya.api.entity.User;
import com.ventrya.api.entity.enums.Role;
import com.ventrya.api.mapper.UserMapper;
import com.ventrya.api.repository.UserRepository;
import com.ventrya.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponse register(RegisterRequestDTO request) {

        String email = normalizeEmail(request.email());

        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "E-mail já cadastrado."
            );
        }

        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setProfessionalArea(request.professionalArea());
        user.setRole(Role.STUDENT);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.saveAndFlush(user);

        String token = jwtService.generateToken(savedUser);

        return new AuthResponse(
                token,
                "Bearer",
                userMapper.toResponse(savedUser)
        );
    }

    public AuthResponse login(LoginRequestDTO request) {

        String email = normalizeEmail(request.email());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            request.password()
                    )
            );
        } catch (AuthenticationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "E-mail ou senha inválidos."
            );
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "E-mail ou senha inválidos."
                        )
                );

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                "Bearer",
                userMapper.toResponse(user)
        );
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
