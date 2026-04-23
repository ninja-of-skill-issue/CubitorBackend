package org.example.cubitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Algorithm {
    @Id
    private Long id;
    private String name;
    private String algorithm;
    @ManyToOne
    private Event event;
}
