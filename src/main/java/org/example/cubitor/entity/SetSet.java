package org.example.cubitor.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SetSet {
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    // --- settings ---
    private Integer font = 0;
    private Integer theme = 0;
    private Integer timerAccuracy = 2;
    private Double celebrationTime = 1.5;
    private Boolean confirmSolveDeletion = true;

    // --- about user ---
    private String bio = "";
    private String cubingGoal = "";
    private String favoriteEvent = "";
}
