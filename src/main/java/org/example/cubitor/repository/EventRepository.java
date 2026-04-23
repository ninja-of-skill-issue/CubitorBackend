package org.example.cubitor.repository;

import org.example.cubitor.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);
}
