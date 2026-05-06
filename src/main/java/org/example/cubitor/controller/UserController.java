package org.example.cubitor.controller;


import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.UserDTO;
import org.example.cubitor.entity.User;
import org.example.cubitor.exception.CustomException;
import org.example.cubitor.repository.UserRepository;
import org.example.cubitor.service.DTOService;
import org.example.cubitor.service.FriendshipService;
import org.example.cubitor.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FriendshipService friendshipService;
    private final DTOService dtoService;

    @PostMapping("/update_user")
    public ResponseEntity<Boolean> editUser(@RequestBody User newUser,
                         @AuthenticationPrincipal User user) {

        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        dtoService.updateEntity(user, newUser);
        userRepository.save(user);

        return ResponseEntity.ok(true);
    }

    @PostMapping("/delete_user")
    public ResponseEntity<Boolean> deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/get_friends")
    public ResponseEntity<List<UserDTO>> getFriends(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDTO = userService.getCurrentUser(userDetails.getUsername());
        List<User> friendUsers = friendshipService.getFriends(userDTO.getId());

        List<UserDTO> friends = friendUsers.stream()
                .map(user -> userService.getCurrentUser(user.getEmail()))
                .toList();

        return ResponseEntity.ok(friends);
    }

    @PostMapping("/add_friend")
    public ResponseEntity<Boolean> addFriend(@RequestBody UserDTO userDTO,
                                             @AuthenticationPrincipal User user) {
        User friend = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new CustomException("Friend-user not found"));

        friendshipService.addFriendship(friend.getId(), user.getId());

        return ResponseEntity.ok(true);
    }

    @PostMapping("/remove_friend")
    public ResponseEntity<Boolean> removeFriend(@RequestBody UserDTO userDTO,
                                                @AuthenticationPrincipal User user) {
        User friend = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new CustomException("Friend-user not found"));

        friendshipService.removeFriendship(friend.getId(), user.getId());

        return ResponseEntity.ok(true);
    }



}
