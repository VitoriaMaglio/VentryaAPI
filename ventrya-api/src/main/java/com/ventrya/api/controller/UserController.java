package com.ventrya.api.controller;

import com.ventrya.api.dto.response.UserResponseDTO;
import com.ventrya.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponseDTO getMyProfile(
            Authentication authentication
    ) {
        return userService.getMyProfile(
                authentication.getName()
        );
    }
}
