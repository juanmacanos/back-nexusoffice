package com.jmcano.gestorpuestos.controller;

import com.jmcano.gestorpuestos.dto.changePassword.ChangePasswordRequest;
import com.jmcano.gestorpuestos.dto.login.LoginRequest;
import com.jmcano.gestorpuestos.dto.register.RegisterRequest;

import com.jmcano.gestorpuestos.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest){
        String jwt = authService.login(loginRequest);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest registerRequest){
        authService.register(registerRequest);
        return ResponseEntity.created(URI.create("/api/auth/login")).body("User registered successfully");
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    public ResponseEntity<String> changePassword(
            @RequestBody @Valid ChangePasswordRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName();
        authService.changePassword(username, request);
        return ResponseEntity.ok("Password changed successfully");
    }

}
