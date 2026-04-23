package org.example.cubitor.entity;


import jakarta.persistence.*;

@Entity
public class Setset {
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    private Integer font;
    private Integer theme;
    private Integer timerAccuracy;
    private Double celebrationTime;
    private Boolean confirmSolveDeletion;
    private String bio;
    private String cubingGoal;
    private String favoriteEvent;
}
