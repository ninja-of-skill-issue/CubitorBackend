package org.example.cubitor.controller;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.PendingFriendshipDTO;
import org.example.cubitor.entity.User;
import org.example.cubitor.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationsController {
    private final FriendshipService friendshipService;


    @PostMapping("/pending_friend_requests")
    public ResponseEntity<List<PendingFriendshipDTO>> pendingFriendRequests(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(friendshipService.getPendingFriendships(user.getId()));
    }



}
