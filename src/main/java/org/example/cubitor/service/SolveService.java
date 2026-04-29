package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.SolveResponse;
import org.example.cubitor.dto.UserResponse;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.entity.User;
import org.example.cubitor.repository.SolveRepository;
import org.example.cubitor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolveService {
    private final SolveRepository solveRepository;
    private final UserRepository userRepository;

    public void addSolve(Solve solve) {
        solve.setId(null);
        solveRepository.save(solve);
    }

    public void deleteSolves(List<Solve> solves) {
        solveRepository.deleteAll(solves);
    }


    private SolveResponse mapToResponse(Solve solve) {
        return SolveResponse.builder()
                .id(solve.getId())
                .user_id(solve.getUser().getId())
                .tim(solve.getTim())
                .note(solve.getNote())
                .creation_date(solve.getCreation_date())
                .penalty(solve.getPenalty())
                .scramble(solve.getScramble())
                .build();
    }
}
