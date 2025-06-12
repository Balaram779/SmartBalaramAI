// File: JwtAuthFilter.java
package com.smartbalaram.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Intercepts every HTTP request once (OncePerRequestFilter):
 * ✅ Extracts the JWT from Authorization header
 * ✅ Validates the token
 * ✅ Loads user details using CustomUserDetailsService
 * ✅ Sets Spring Security context (authenticated user)
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    /**
     * Core filter logic to extract and validate JWT on every request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Proceed if no Authorization header or does not start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from header
        final String jwt = authHeader.substring(7); // Remove "Bearer "
        final String username = jwtService.extractUsername(jwt);

        // If username is valid and no authentication is set yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate token with user details
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Create Spring Security Authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Attach request-specific details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Save authentication to context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Pass to next filter in chain
        filterChain.doFilter(request, response);
    }
}
