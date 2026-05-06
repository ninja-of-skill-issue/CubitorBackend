package org.example.cubitor.controller;


import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.SolveDTO;
import org.example.cubitor.dto.UserDTO;

import org.example.cubitor.exception.CustomException;
import org.example.cubitor.service.DTOService;
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
    private final UserRepository userRepository;
    private final DTOService dtoService;


    @PostMapping("/user_info")
    public ResponseEntity<UserDTO> mainPage(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found"));

        return ResponseEntity.ok(dtoService.toDTO(user));
    }

    @PostMapping("/solves_info")
    public ResponseEntity<List<SolveDTO>> solvesByUser(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found"));

        List<SolveDTO> solvesByUser = userService.getSolvesByUser(user);

        return ResponseEntity.ok(solvesByUser);
    }

    @PostMapping("all_users")
    public ResponseEntity<List<UserDTO>> allUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
