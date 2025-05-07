package com.jmcano.gestorpuestos.controller;

import com.jmcano.gestorpuestos.dto.profile.ProfileResponse;
import com.jmcano.gestorpuestos.model.User;
import com.jmcano.gestorpuestos.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(Authentication authentication){
        String username = (String) authentication.getPrincipal();
        return ResponseEntity.ok(profileService.getProfile(username));
    }


}
