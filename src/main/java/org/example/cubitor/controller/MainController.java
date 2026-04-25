package org.example.cubitor.controller;


import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.UserResponse;
import org.example.cubitor.dto.SolvesByUserResponse;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.repository.SolveRepository;
import org.example.cubitor.service.AuthService;
import org.example.cubitor.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.example.cubitor.entity.User;
import org.example.cubitor.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final SolveRepository solveRepository;
    private final UserRepository userRepository; // ← додати
    private final AuthService authService;


    @PostMapping("/user_info")
    public ResponseEntity<UserResponse> mainPage(Authentication authentication) {

        String email = authentication.getName();

        UserResponse userResponse = userService.getCurrentUser(email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/solves_info")
    public ResponseEntity<SolvesByUserResponse> solvesByUser(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SolvesByUserResponse solvesByUser = userService.getSolvesByUser(user);

        return ResponseEntity.ok(solvesByUser);
    }
}
