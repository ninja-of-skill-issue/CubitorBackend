package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.config.JwtService;
import org.example.cubitor.dto.*;
import org.example.cubitor.entity.Role;
import org.example.cubitor.entity.User;
import org.example.cubitor.exception.CustomException;
import org.example.cubitor.repository.UserRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthDTO register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("User already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .account_creation_date(timeView())
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthDTO(token);
    }

    public AuthDTO login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("Wrong credentials"));

        String storedPassword = user.getPassword();
        if (storedPassword == null || !passwordEncoder.matches(request.getPassword(), storedPassword)) {
            throw new CustomException("Wrong credentials");
        }

        String token = jwtService.generateToken(user);
        return new AuthDTO(token);
    }

    public static String timeView() {
        DateTime nowUtc = new DateTime(DateTimeZone.UTC);
        DateTime nowKyiv = nowUtc.toDateTime(DateTimeZone.forID("Europe/Kiev"));
        String time = nowKyiv.toString();
        return time.substring(11, 19);
    }
}
