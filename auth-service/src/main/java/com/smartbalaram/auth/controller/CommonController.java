package com.smartbalaram.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Sample protected endpoint that requires authentication (via JWT).
 */
@RestController
@RequestMapping("/api/common")
public class CommonController {

    @GetMapping("/data")
    public ResponseEntity<String> getCommonData() {
        return ResponseEntity.ok("✅ Access granted: This is protected data from /api/common/data");
    }
}
