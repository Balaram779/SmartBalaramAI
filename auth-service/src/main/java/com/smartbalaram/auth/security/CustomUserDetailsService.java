// File: CustomUserDetailsService.java
package com.smartbalaram.auth.security;

import com.smartbalaram.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * Loads user data from MongoDB using the email (username).
 * Injected into the SecurityConfig for authentication provider.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads the user by username (email in this case) for authentication.
     *
     * @param username the email of the user trying to log in
     * @return UserDetails required by Spring Security
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username) // Or findByEmail based on repo
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
