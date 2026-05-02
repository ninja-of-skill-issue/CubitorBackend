package org.example.cubitor.repository;

import org.example.cubitor.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findAllByUserId(Long userId);
    List<Friendship> findAllByFriendId(Long friendId);
}
