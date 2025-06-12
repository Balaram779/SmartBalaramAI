package com.smartbalaram.auth.controller;

import com.smartbalaram.auth.model.AuthRequest;
import com.smartbalaram.auth.model.AuthResponse;
import com.smartbalaram.auth.model.RegisterRequest;
import com.smartbalaram.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that exposes public endpoints for user registration and login.
 * All authentication logic is delegated to the AuthService.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor // Generates constructor for final fields (authService)
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint: POST /api/auth/register
     * Registers a new user and returns a JWT token on success.
     *
     * @param request the registration details (email, password, etc.)
     * @return JWT token wrapped inside AuthResponse
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Endpoint: POST /api/auth/login
     * Authenticates a user with credentials and returns a JWT token.
     *
     * @param request the login credentials (email and password)
     * @return JWT token wrapped inside AuthResponse
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
