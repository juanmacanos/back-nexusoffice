package com.jmcano.gestorpuestos.service;

import com.jmcano.gestorpuestos.dto.profile.ProfileResponse;
import com.jmcano.gestorpuestos.model.User;
import com.jmcano.gestorpuestos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileResponse getProfile(String username) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        ProfileResponse profile = new ProfileResponse();
        profile.setId(user.getId());
        profile.setName(user.getName());
        profile.setEmail(user.getEmail());
        profile.setRole(user.getRole().name());

        return profile;
    }


}
