package com.jmcano.gestorpuestos.service;

import com.jmcano.gestorpuestos.dto.changePassword.ChangePasswordRequest;
import com.jmcano.gestorpuestos.dto.login.LoginRequest;
import com.jmcano.gestorpuestos.dto.register.RegisterRequest;
import com.jmcano.gestorpuestos.model.Role;
import com.jmcano.gestorpuestos.model.User;
import com.jmcano.gestorpuestos.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("Invalid Credentials"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }

        return jwtService.generateToken(user.getName(), user.getRole().name(), user.getId());

    }

    public void register(RegisterRequest registerRequest){
        if(userRepository.findByName(registerRequest.getUsername()).isPresent()){
            throw new RuntimeException("Username already taken");
        }

        User user = User.builder()
                .name(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .role(Role.MEMBER)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        userRepository.save(user);
    }

    public void changePassword(String username, ChangePasswordRequest request){
        User user = userRepository.findByName(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }
}
