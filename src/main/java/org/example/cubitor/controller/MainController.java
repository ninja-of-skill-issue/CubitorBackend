package org.example.cubitor.controller;


import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.UserResponse;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.repository.SolveRepository;
import org.example.cubitor.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final SolveRepository solveRepository;

    @GetMapping("/main_page")
    public ResponseEntity<UserResponse> mainPage(Authentication authentication) {

        String email = authentication.getName(); // берём из JWT

        UserResponse user = userService.getCurrentUser(email);

        return ResponseEntity.ok(user);
    }
}
