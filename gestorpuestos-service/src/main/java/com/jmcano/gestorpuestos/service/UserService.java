package com.jmcano.gestorpuestos.service;

import com.jmcano.gestorpuestos.dto.user.UserRequest;
import com.jmcano.gestorpuestos.dto.user.UserResponse;
import com.jmcano.gestorpuestos.mapper.UserMapper;
import com.jmcano.gestorpuestos.model.Place;
import com.jmcano.gestorpuestos.model.User;
import com.jmcano.gestorpuestos.repository.PlaceRepository;
import com.jmcano.gestorpuestos.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        return userMapper.toResponse(userRepository.save(user));
    }

    public List<UserResponse> getAll(Authentication authentication) {

        List<User> users = userRepository.findAll();

        return userMapper.toResponse(users);
    }
}
