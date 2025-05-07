package com.jmcano.gestorpuestos.controller;

import com.jmcano.gestorpuestos.dto.user.UserResponse;
import com.jmcano.gestorpuestos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);

        URI location = URI.create("/api/users/" + userResponse.getId());

        return ResponseEntity.created(location).body(userResponse);
    }
     */

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(Authentication authentication) {
        List<UserResponse> users = userService.getAll(authentication);
        return ResponseEntity.ok(users);
    }

}
