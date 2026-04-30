package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.SolveResponse;
import org.example.cubitor.dto.UserResponse;
import org.example.cubitor.dto.SolvesByUserResponse;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.entity.User;
import org.example.cubitor.exception.CustomException;
import org.example.cubitor.repository.SolveRepository;
import org.example.cubitor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SolveRepository solveRepository;

    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found"));

        return mapToResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .elo(String.valueOf(user.getElo()))
                .description(user.getDescription())
                .avatar(user.getAvatar())
                .last_online(user.getLast_online())
                .username(user.getTheActualUsername())
                .account_creation_date(user.getAccount_creation_date())
                .friends(user.getFriends().toString())
                .build();
    }

    public SolvesByUserResponse getSolvesByUser(User user) {
        List<Solve> solves = solveRepository.findAllByUser(user);

        List<SolveResponse> solveResponses = solves.stream()
                .map(s -> SolveResponse.builder()
                        .id(s.getId())
                        .tim(s.getTim())
                        .scramble(s.getScramble())
                        .creation_date(s.getCreation_date())
                        .note(s.getNote())
                        .penalty(s.getPenalty())
                        .build())
                .collect(Collectors.toList());

        return SolvesByUserResponse.builder()
                .solves(solveResponses)
                .build();
    }
}
