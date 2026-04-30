package org.example.cubitor.controller;


import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.cubitor.dto.UserResponse;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.entity.User;
import org.example.cubitor.repository.SolveRepository;
import org.example.cubitor.repository.UserRepository;
import org.example.cubitor.service.SolveService;
import org.example.cubitor.service.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SolveController {
    private final SolveService solveService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final SolveRepository solveRepository;

    @PostMapping("/add_solve")
    public ResponseEntity<Solve> createSolve(@RequestBody Solve solve,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        solve.setId(null);
        insertUserToSolve(solve, userDetails);
        solveService.addSolve(solve);
        System.out.println(solve);
        return ResponseEntity.ok(solve);
    }

    @PostMapping("/delete_solves")
    private ResponseEntity<Boolean> deleteSolves(@RequestBody List<Solve> solves,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        for (Solve solve : solves) {
            insertUserToSolve(solve, userDetails);
        }

        boolean success = solveService.deleteSolves(solves);

        return success ? ResponseEntity.ok(true) : ResponseEntity.ok(false);
    }

    @PostMapping("/edit_solves")
    public ResponseEntity<List<Solve>> editSolves(@RequestBody List<Solve> solves,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        List<Solve> unsuccessfullyEdited = new ArrayList<>();
        List<Solve> toSave = new ArrayList<>();

        for (Solve solve : solves) {
            if (solve.getId() == null || !solveRepository.existsById(solve.getId())) {
                unsuccessfullyEdited.add(solve);
            } else {
                // Только устанавливаем пользователя, сохраним всё разом позже
                UserResponse userResponse = userService.getCurrentUser(userDetails.getUsername());
                User user = userRepository.findByEmail(userResponse.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                solve.setUser(user);
                toSave.add(solve);
            }
        }

        solveRepository.saveAll(toSave); // Пакетное сохранение быстрее
        solves.forEach(System.out::println);
        return ResponseEntity.ok(unsuccessfullyEdited);
    }


    private void insertUserToSolve(Solve solve, UserDetails userDetails) {
        UserResponse userResponse = userService.getCurrentUser(userDetails.getUsername());
        User user = userRepository.findByEmail(userResponse.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        solve.setUser(user);
        solveService.addSolve(solve);
    }

}
