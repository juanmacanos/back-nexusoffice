package com.jmcano.gestorpuestos.config;

import com.jmcano.gestorpuestos.model.Role;
import com.jmcano.gestorpuestos.model.User;
import com.jmcano.gestorpuestos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminUserInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            if (userRepository.findByName("admin").isEmpty()) {
                User admin = User.builder()
                        .name("admin")
                        .email("admin@admin.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
                System.out.println("Admin user created: admin@admin.com/admin123");
            }
        };
    }
}
