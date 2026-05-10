package org.example.cubitor.controller;


import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.SolveDTO;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.entity.User;
import org.example.cubitor.exception.CustomException;
import org.example.cubitor.repository.SolveRepository;
import org.example.cubitor.repository.UserRepository;
import org.example.cubitor.service.DTOService;
import org.example.cubitor.service.SolveService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SolveController {
    private final SolveService solveService;
    private final UserRepository userRepository;
    private final SolveRepository solveRepository;
    private final DTOService dtoService;

    @PostMapping("/add_solve")
    public ResponseEntity<SolveDTO> createSolve(@RequestBody SolveDTO solveDTO,
                                               @AuthenticationPrincipal User user) {
        Solve solve = dtoService.toEntity(solveDTO);
        solve.setId(null);
        insertUserToSolve(solve, user);
        Solve savedSolve = solveRepository.save(solve);

        return ResponseEntity.ok(dtoService.toDTO(savedSolve));
    }

    @PostMapping("/delete_solves")
    private ResponseEntity<Boolean> deleteSolves(@RequestBody List<Solve> solves,
                                                 @AuthenticationPrincipal User user) {
        for (Solve solve : solves) {
            insertUserToSolve(solve, user);
        }

        boolean success = solveService.deleteSolves(solves);

        return success ? ResponseEntity.ok(true) : ResponseEntity.ok(false);
    }

    @PostMapping("/edit_solves")
    public ResponseEntity<List<SolveDTO>> editSolves(@RequestBody List<Solve> solves,
                                                     @AuthenticationPrincipal User user) {
        solves.forEach(s -> insertUserToSolve(s, user));

        return ResponseEntity.ok(solveService.editSolves(solves).stream()
                .map(dtoService::toDTO)
                .toList());
    }


    private void insertUserToSolve(Solve solve, User user) {
        User newUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomException("User not found"));

        solve.setUser(newUser);
        solveRepository.save(solve);
    }

}
