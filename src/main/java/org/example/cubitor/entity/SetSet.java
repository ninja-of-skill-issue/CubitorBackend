package org.example.cubitor.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class SetSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    // --- settings ---
    private Integer font = 0;
    private Integer theme = 0;
    private Integer timerAccuracy = 2;
    private Double celebrationTime = 1.5;
    private Boolean confirmSolveDeletion = true;
    private Integer widgetCount = 4;
    private Boolean useInspection = false;
    private Boolean saveMinigameSolves = false;

    // --- about user ---
    private String bio = "";
    private String cubingGoal = "";
    private String favoriteEvent = "";

    private WidgetConfig widgetConfig = new WidgetConfig(List.of(1, 2, 3, 4, 5), false);
    private WidgetConfig minigameConfig = new WidgetConfig(List.of(1, 3, 6, 7, 0), true);

    @ElementCollection
    private List<StatBlock> statConfig = List.of(
        new StatBlock(List.of(0, 1, 1, 1)),   // best-single|medium
        new StatBlock(List.of(0, 1, 0, 1)),   // cur-single|medium
        new StatBlock(List.of(1, 5, 0, 0)),   // cur-ao5|normal
        new StatBlock(List.of(1, 12, 0, 0)),  // cur-ao12|normal
        new StatBlock(List.of(1, 50, 0, 0)),  // cur-ao50|normal
        new StatBlock(List.of(1, 5, 1, 0)),   // best-ao5|normal
        new StatBlock(List.of(1, 12, 1, 0)),  // best-ao12|normal
        new StatBlock(List.of(2, 1, 0, 0))    // cur-mean|normal
    );

    @Embeddable @Data
    public static class WidgetConfig {
        /* id | widget
            0 | empty / none
            1 | solves
            2 | stats
            3 | scramble
            4 | graph
            5 | columns
            6 | game ui
            7 | chat
         */
        private Integer n1;
        private Integer n2;
        private Integer n3;
        private Integer n4;
        private Integer n5;

        private boolean isMinigame;

        public WidgetConfig() {}
        public WidgetConfig(List<Integer> config, boolean minigame) {
            setConfig(config);
            this.isMinigame = minigame;
        }

        @Transient
        public List<Integer> getConfig() {
            return List.of(n1, n2, n3, n4, n5);
        }

        public void setConfig(List<Integer> config) {
            if (config.size() < 5) throw new IllegalArgumentException();
            this.n1 = config.get(0);
            this.n2 = config.get(1);
            this.n3 = config.get(2);
            this.n4 = config.get(3);
            this.n5 = config.get(4);
        }
    }

    @Embeddable @Data
    public static class StatBlock {
        private Integer type; // stat type (0=single, 1=ao, 2=mean)
        private Integer amount; // average of ? (1 for single, 5, 12, etc.)
        private Integer time; // time of stat (0=current, 1=best, 2=worst)
        private Integer size; // visual size (0=normal, 1=medium, 2=large)

        public StatBlock() {}
        public StatBlock(List<Integer> block) {
            setBlock(block);
        }

        @Transient
        public List<Integer> getBlock() {
            return List.of(type, amount, time, size);
        }

        public void setBlock(List<Integer> block) {
            this.type = block.get(0);
            this.amount = block.get(1);
            this.time = block.get(2);
            this.size = block.size() > 3 ? block.get(3) : 0;
        }
    }
}
