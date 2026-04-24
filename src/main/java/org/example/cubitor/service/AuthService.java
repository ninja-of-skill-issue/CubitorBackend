package org.example.cubitor.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.cubitor.config.JwtService;
import org.example.cubitor.dto.*;
import org.example.cubitor.entity.Role;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.entity.User;
import org.example.cubitor.exception.CustomException;
import org.example.cubitor.repository.SolveRepository;
import org.example.cubitor.repository.UserRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final SolveRepository solveRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("Користувач уже існує");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .accaunt_creation_date(timeView())
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("Невірні облікові дані"));

        String storedPassword = user.getPassword();
        if (storedPassword == null || !passwordEncoder.matches(request.getPassword(), storedPassword)) {
            throw new CustomException("Невірні облікові дані");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public static String timeView() {
        DateTime nowUtc = new DateTime(DateTimeZone.UTC);
        DateTime nowKyiv = nowUtc.toDateTime(DateTimeZone.forID("Europe/Kiev"));
        String time = nowKyiv.toString();
        return time.substring(11, 19);
    }
}
