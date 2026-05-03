package org.example.cubitor.controller;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.PendingFriendshipResponse;
import org.example.cubitor.dto.UserResponse;
import org.example.cubitor.entity.Friendship;
import org.example.cubitor.entity.User;
import org.example.cubitor.repository.FriendshipRepository;
import org.example.cubitor.repository.UserRepository;
import org.example.cubitor.service.FriendshipService;
import org.example.cubitor.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FriendshipService friendshipService;
    private final FriendshipRepository friendshipRepository;

    @PostMapping("/update_user")
    public ResponseEntity<Boolean> editUser(@RequestBody User newUser,
                         @AuthenticationPrincipal UserDetails userDetails) {
        // 1. Получаем текущего пользователя из базы
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Обновляем обычные поля
        user.setAvatar(newUser.getAvatar());
        user.setElo(newUser.getElo());
        user.setDescription(newUser.getDescription());
        user.setUsername(newUser.getTheActualUsername());
        System.out.println(newUser.getDescription());

        // 3. Обновляем пароль ТОЛЬКО если он пришел в запросе
        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            // Обязательно хешируем перед сохранением!
            // passwordEncoder должен быть внедрен через конструктор
            String encodedPassword = passwordEncoder.encode(newUser.getPassword());
            user.setPassword(encodedPassword);
        }

        userRepository.save(user);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/delete_user")
    public ResponseEntity<Boolean> deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/get_friends")
    public ResponseEntity<List<UserResponse>> getFriends(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponse userResponse = userService.getCurrentUser(userDetails.getUsername());
        List<User> friendUsers = friendshipService.getFriends(userResponse.getId());

        List<UserResponse> friends = friendUsers.stream()
                .map(user -> userService.getCurrentUser(user.getEmail()))
                .toList();

        return ResponseEntity.ok(friends);
    }

    @PostMapping("/add_friend")
    public ResponseEntity<Boolean> addFriend(@RequestBody UserResponse userResponse,
                                             @AuthenticationPrincipal User user) {
        User friend = userRepository.findByEmail(userResponse.getEmail())
                .orElseThrow(() -> new RuntimeException("Friend-user not found"));

        friendshipService.addFriendship(friend.getId(), user.getId());

        return ResponseEntity.ok(true);
    }

    @PostMapping("/remove_friend")
    public ResponseEntity<Boolean> removeFriend(@RequestBody UserResponse userResponse,
                                                @AuthenticationPrincipal User user) {
        User friend = userRepository.findByEmail(userResponse.getEmail())
                .orElseThrow(() -> new RuntimeException("Friend-user not found"));

        friendshipService.removeFriendship(friend.getId(), user.getId());

        return ResponseEntity.ok(true);
    }



}
