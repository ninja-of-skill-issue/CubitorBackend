package org.example.cubitor.repository;

import org.example.cubitor.dto.UserResponse;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolveRepository extends JpaRepository<Solve, Long> {

    List<Solve> findAllByUser(User user);

    List<Solve> findAllById(Long id);
}
