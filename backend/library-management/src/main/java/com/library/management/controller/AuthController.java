package com.library.management.controller;

import com.library.management.payload.AuthRequest;
import com.library.management.payload.AuthResponse;
import com.library.management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateSession(@RequestHeader("Authorization") String token) {
        AuthResponse response = authService.validateSession(token);
        return ResponseEntity.ok(response);

    }


}