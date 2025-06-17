// File: AuthService.java
package com.smartbalaram.auth.service;

import com.smartbalaram.auth.model.AuthRequest;
import com.smartbalaram.auth.model.AuthResponse;
import com.smartbalaram.auth.model.RegisterRequest;
import com.smartbalaram.auth.model.User;
import com.smartbalaram.auth.repository.UserRepository;
import com.smartbalaram.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsible for user registration and authentication logic.
 * 
 * 🚀 Key Operations:
 * - Register new user and return JWT token
 * - Login (authenticate) and return JWT token
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user with encoded password and role.
     * 
     * @param request contains username, password, and role
     * @return JWT token wrapped in AuthResponse
     */
    public AuthResponse register(RegisterRequest request) {
        // ✅ Build user entity
    	User user = User.builder()
                .email(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // 🗂️ Save to MongoDB
    	User  savedUser=userRepository.save(user);
        log.info("✅ Registered user: {}", savedUser.getEmail());
        // 🎟️ Generate JWT
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    /**
     * Logs in an existing user by verifying credentials and returns JWT.
     * 
     * @param request contains username and password
     * @return JWT token wrapped in AuthResponse
     */
    public AuthResponse login(AuthRequest request) {
    	
    	try {
    	log.info("🔐 Attempting login for: {}", request.getUsername());
        // 🔐 Authenticate credentials using Spring Security
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        // 🧾 Fetch user from DB
        User user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🎟️ Generate JWT
        String jwtToken = jwtService.generateToken(user);
        log.info("✅ Login success, JWT generated for: {}", user.getUsername());


        return new AuthResponse(jwtToken);
    	} catch (Exception ex) {
            log.error("❌ Login failed for user '{}': {}", request.getUsername(), ex.getMessage());
            throw ex;

    	}
    }
}
