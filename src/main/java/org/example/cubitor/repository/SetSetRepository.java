package org.example.cubitor.repository;

import org.example.cubitor.entity.SetSet;
import org.example.cubitor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SetSetRepository extends JpaRepository<SetSet, Long> {
    Optional<SetSet> findByUser(User user);

    Optional<SetSet> findByUserId(Long userId);

}
