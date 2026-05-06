package org.example.cubitor.repository;

import org.example.cubitor.entity.Settings;
import org.example.cubitor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Optional<Settings> findByUser(User user);

}
