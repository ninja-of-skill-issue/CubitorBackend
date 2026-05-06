package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.PendingFriendshipDTO;
import org.example.cubitor.entity.Friendship;
import org.example.cubitor.entity.User;
import org.example.cubitor.repository.FriendshipRepository;
import org.example.cubitor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Friendship findFriendshipByUsers(Long id1, Long id2) {
        List<Friendship> asUser = friendshipRepository.findAllByUserId(id1);
        List<Friendship> asFriend = friendshipRepository.findAllByFriendId(id1);

        return Stream.concat(asUser.stream(), asFriend.stream())
                .filter(f -> f.getUser().getId().equals(id2) || f.getFriend().getId().equals(id2))
                .findFirst().orElse(null);
    }

    public void addFriendship(Long requestingId, Long friendId) {
        var users = find2UsersById(requestingId, friendId);
        User requstingUser = users.getFirst();
        User friend = users.getLast();

        Friendship friendship = findFriendshipByUsers(requestingId, friendId);
        if (friendship != null) {
            if (friendship.getFriendAccepted()) friendship.setUserAccepted(true);
            else friendship.setFriendAccepted(true);
        } else {
            friendship = new Friendship();
            friendship.setFriend(friend);
            friendship.setUser(requstingUser);
            friendship.setUserAccepted(true);
            requstingUser.getFriendships().add(friendship);
            friend.getFriendships().add(friendship);
        }
        friendshipRepository.save(friendship);
        userRepository.save(requstingUser);
        userRepository.save(friend);

    }

    public void removeFriendship(Long requestingId, Long friendId) {
        var users = find2UsersById(requestingId, friendId);
        User requstingUser = users.getFirst();
        User friend = users.getLast();

        Friendship friendship = findFriendshipByUsers(requestingId, friendId);
        if (friendship == null) return;

        friendshipRepository.delete(friendship);
        requstingUser.getFriendships().remove(friendship);
        friend.getFriendships().remove(friendship);
        userRepository.save(requstingUser);
        userRepository.save(friend);
    }

    public List<User> getFriends(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));


        // filtering pending friendships
        // getting the friend out of the friendship

        return user.getFriendships().stream()
                .filter(f -> f.getFriendAccepted() && f.getUserAccepted()) // filtering pending friendships
                .map(f -> f.getMyFriend(user)) // getting the friend out of the friendship
                .toList();
    }

    public List<PendingFriendshipDTO> getPendingFriendships(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFriendships().stream()
                .filter(f -> !(f.getFriendAccepted() && f.getUserAccepted()))
                .map(this::mapToPendingDTO)
                .toList();
    }

    private List<User> find2UsersById(Long id1, Long id2) {
        User user1 = userRepository.findById(id1).orElseThrow(() -> new RuntimeException("User1 not found"));
        User user2 = userRepository.findById(id2).orElseThrow(() -> new RuntimeException("User2 not found"));

        return List.of(user1, user2);
    }
    private PendingFriendshipDTO mapToPendingDTO(Friendship friendship) {
        if (friendship.getFriendAccepted() && friendship.getUserAccepted()) return null;
        User acceptedOne = friendship.getUserAccepted() ? friendship.getUser() : friendship.getFriend();
        User pendingOne = (acceptedOne == friendship.getUser()) ? friendship.getFriend() : friendship.getUser();

        return PendingFriendshipDTO.builder()
                .user(userService.getCurrentUser(acceptedOne.getEmail()))
                .destination(userService.getCurrentUser(pendingOne.getEmail()))
                .build();

    }

}
