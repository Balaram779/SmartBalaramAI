package com.smartbalaram.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> getUserDashboard() {
        return ResponseEntity.ok("✅ USER role: Welcome to your dashboard!");
    }
}
