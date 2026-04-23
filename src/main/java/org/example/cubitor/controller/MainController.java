package org.example.cubitor.controller;


import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.AuthResponse;
import org.example.cubitor.dto.RegisterRequest;
import org.example.cubitor.dto.UserResponse;
import org.example.cubitor.dto.UserWithSolvesResponse;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.repository.SolveRepository;
import org.example.cubitor.service.AuthService;
import org.example.cubitor.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.example.cubitor.entity.User;
import org.example.cubitor.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final SolveRepository solveRepository;
    private final UserRepository userRepository; // ← додати
    private final AuthService authService;


    @PostMapping("/main_page")
    public ResponseEntity<UserWithSolvesResponse> mainPage(Authentication authentication) {

        String email = authentication.getName();

        UserResponse userResponse = userService.getCurrentUser(email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Solve> solves = solveRepository.findAllByUser(user); // ← тепер User

        UserWithSolvesResponse userWithSolves = authService.getUserWithSolves(user);

        return ResponseEntity.ok(userWithSolves);
    }
}
