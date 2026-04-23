package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.UserResponse;
import org.example.cubitor.entity.User;
import org.example.cubitor.exception.CustomException;
import org.example.cubitor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
                .build();
    }
}
