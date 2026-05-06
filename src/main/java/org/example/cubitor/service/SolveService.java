package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;

import org.example.cubitor.entity.Solve;
import org.example.cubitor.repository.SolveRepository;
import org.example.cubitor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolveService {
    private final SolveRepository solveRepository;
    private final UserRepository userRepository;

    public List<Solve> editSolves(List<Solve> solves) {
        List<Solve> notEdited = new ArrayList<>();
        List<Solve> saved = new ArrayList<>();

        for (Solve solve : solves) {
            if (solveRepository.findAllById(solve.getId()) == null) {
                notEdited.add(solve);
                continue;
            }
            if (userRepository.findById(solve.getUser().getId()).isEmpty())
                continue;
            saved.add(solve);
        }

        solveRepository.saveAll(saved);
        return notEdited;
    }

    public boolean deleteSolves(List<Solve> solves) {
        if (solves == null) return false;

        for (Solve solve : solves)
            if (solveRepository.findAllById(solve.getId()).isEmpty())
                return false;

        solveRepository.deleteAll(solves);
        return true;
    }



}
