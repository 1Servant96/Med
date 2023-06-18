package com.medcheck.api;

import com.medcheck.db.service.UserService;
import com.medcheck.dto.request.RegisterRequest;
import com.medcheck.dto.request.UserRequest;
import com.medcheck.dto.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@Tag(name = "Auth API", description = "Authentication API")
public class AuthApi {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Sign in", description = "Any user can authenticate")
    public AuthResponse login(@RequestBody UserRequest request) {
        return userService.login(request);
    }

    @PostMapping("/registration")
    @Operation(summary = "Sign up", description = "Any user can register")
    public AuthResponse create(@RequestBody RegisterRequest request) {
        return userService.registration(request);
    }

}
