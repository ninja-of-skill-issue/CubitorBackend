package org.example.cubitor.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.cubitor.config.JwtService;
import org.example.cubitor.dto.AuthResponse;
import org.example.cubitor.dto.LoginRequest;
import org.example.cubitor.dto.RegisterRequest;
import org.example.cubitor.entity.Role;
import org.example.cubitor.entity.User;
import org.example.cubitor.exception.CustomException;
import org.example.cubitor.repository.UserRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("User already exists");
        }

        if (createdAt == null) createdAt = LocalDateTime.now();

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .accaunt_creation_date(createdAt.toString())
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("User not found"));

        String storedPassword = user.getPassword();
        if (storedPassword == null || !passwordEncoder.matches(request.getPassword(), storedPassword)) {
            throw new CustomException("Wrong password");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
