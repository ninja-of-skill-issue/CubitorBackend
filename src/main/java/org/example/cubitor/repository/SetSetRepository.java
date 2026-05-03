package org.example.cubitor.repository;

import org.example.cubitor.entity.SetSet;
import org.example.cubitor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetSetRepository extends JpaRepository<SetSet, Long> {
    List<SetSet> findAllByUser(User user);
}
