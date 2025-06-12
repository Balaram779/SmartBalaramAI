// File: User.java
package com.smartbalaram.auth.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Represents an application user stored in MongoDB and used for Spring Security authentication.
 * Implements UserDetails to integrate with Spring Security's user management.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users") // MongoDB collection name
public class User implements UserDetails {

    @Id
    private String id;

    private String email;    // Used as username
    private String password; // Encrypted password (BCrypt, etc.)
    private String role;     // Example: "ADMIN", "USER"

    /**
     * Returns user's authorities for Spring Security.
     * Example: If role is "ADMIN", this returns "ROLE_ADMIN".
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role);
    }

    // Used by Spring Security during login and validation checks

    @Override public String getUsername() { return email; }

    @Override public boolean isAccountNonExpired() { return true; }

    @Override public boolean isAccountNonLocked() { return true; }

    @Override public boolean isCredentialsNonExpired() { return true; }

    @Override public boolean isEnabled() { return true; }
}
