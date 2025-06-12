// File: SecurityConfig.java
package com.smartbalaram.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Main Spring Security configuration.
 * 
 * 🔐 Configures:
 * - JWT filter before UsernamePasswordAuthenticationFilter
 * - Public and protected endpoints
 * - Stateless session policy (no HTTP sessions)
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Main Security Filter Chain configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs (since stateless)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/auth/**",              // Public auth endpoints
                    "/swagger-ui/**",            // Swagger UI
                    "/v3/api-docs/**"            // OpenAPI docs
                ).permitAll()

                .requestMatchers("/api/users/**").hasRole("USER")   // USER-only APIs
                .requestMatchers("/api/admin/**").hasRole("ADMIN")  // ADMIN-only APIs
                .requestMatchers("/api/common/**").authenticated()  // Any authenticated user
                .anyRequest().denyAll()                             // Deny anything else
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session storage
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // 🔑 JWT before auth

        return http.build();
    }

    /**
     * Exposes AuthenticationManager as a Spring Bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * BCrypt password encoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Recommended encoder
    }
}
