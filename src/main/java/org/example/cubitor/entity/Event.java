package org.example.cubitor.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Event {
    public enum ScrambleGenerationType {
        X2, X3, X4, X5, X6, X7, PYRA, MINX, SQ1, SKEWB, CLOCK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private ScrambleGenerationType scrambleGenerationType;

    @OneToMany(mappedBy = "event")
    private List<Solve> solves;

    public Event(String name, ScrambleGenerationType scrambleGenerationType) {
        this.name = name;
        this.scrambleGenerationType = scrambleGenerationType;
    }
}
