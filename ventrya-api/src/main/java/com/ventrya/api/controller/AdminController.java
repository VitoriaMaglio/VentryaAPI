package com.ventrya.api.controller;


import com.ventrya.api.dto.response.UserResponseDTO;
import com.ventrya.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDTO>> findAllUsers(
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                userService.findAll(pageable)
        );
    }
}
