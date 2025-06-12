package com.smartbalaram.auth.model;

import lombok.Data;

/**
 * DTO for registration request.
 */
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role;
}
