package com.smartbalaram.auth.model;

import lombok.Data;

/**
 * DTO for login request.
 */
@Data
public class AuthRequest {
    private String username;
    private String password;
}
