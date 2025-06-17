package com.smartbalaram.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/secure-data")
    public ResponseEntity<String> getAdminData() {
        return ResponseEntity.ok("🔐 ADMIN role: Here's your secure admin data.");
    }
}
