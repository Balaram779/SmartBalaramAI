package com.smartbalaram.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for auth response containing JWT.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
