package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.SolveDTO;
import org.example.cubitor.dto.UserDTO;
import org.example.cubitor.entity.User;
import org.example.cubitor.exception.CustomException;
import org.example.cubitor.repository.SolveRepository;
import org.example.cubitor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SolveRepository solveRepository;
    private final DTOService dtoService;

    public UserDTO getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found"));

        return dtoService.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(dtoService::toDTO)
                .toList();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new CustomException("User not found");
        userRepository.deleteById(id);
    }

    public List<SolveDTO> getSolvesByUser(User user) {
        return solveRepository.findAllByUser(user).stream()
                .map(dtoService::toDTO)
                .toList();
    }
}
