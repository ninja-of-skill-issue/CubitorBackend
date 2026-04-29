package org.example.cubitor.controller;


import lombok.RequiredArgsConstructor;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.service.SolveService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add_solve")
    public ResponseEntity<Boolean> createSolve(@RequestBody Solve solve) {
        solveService.addSolve(solve);
        return ResponseEntity.ok(true);
    }

    @PostMapping("delete_solves")
    private ResponseEntity<Boolean> deleteSolves(@RequestBody List<Solve> solves) {
        solveService.deleteSolves(solves);
        return ResponseEntity.ok(true);
    }

}
