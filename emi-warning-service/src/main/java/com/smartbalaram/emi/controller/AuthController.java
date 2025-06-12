package com.smartbalaram.emi.controller;

import com.smartbalaram.emi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @GetMapping("/token")
    public String getToken(@RequestParam String username) {
        return jwtUtil.generateToken(username);
    }
}
