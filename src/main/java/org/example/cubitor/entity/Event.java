package org.example.cubitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Event {
    @Id
    private Long id;
    private String name;
}
