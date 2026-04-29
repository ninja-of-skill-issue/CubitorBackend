package org.example.cubitor.controller;


import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.cubitor.dto.UserResponse;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.entity.User;
import org.example.cubitor.repository.UserRepository;
import org.example.cubitor.service.SolveService;
import org.example.cubitor.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SolveController {
    private final SolveService solveService;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/add_solve")
    public ResponseEntity<Boolean> createSolve(@RequestBody Solve solve,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        UserResponse userResponse = userService.getCurrentUser(userDetails.getUsername());
        User user = userRepository.findByEmail(userResponse.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        solve.setUser(user);
        solveService.addSolve(solve);
        return ResponseEntity.ok(true);
    }

    @PostMapping("delete_solves")
    private ResponseEntity<Boolean> deleteSolves(@RequestBody List<Solve> solves) {
        solveService.deleteSolves(solves);
        return ResponseEntity.ok(true);
    }

}
