package org.example.cubitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingsDTO {
    private Long id;

    // settings
    private Integer font;
    private Integer theme;
    private Integer timerAccuracy;
    private Double celebrationTime;
    private Boolean confirmSolveDeletion;
    private Integer widgetCount;
    private Boolean useInspection;
    private Boolean saveMinigameSolves;

    // about user
    private String bio;
    private String cubingGoal;
    private String favoriteEvent;

    // configs
    private WidgetConfigDTO widgetConfig;
    private WidgetConfigDTO minigameConfig;
    private List<StatBlockDTO> statConfig;

    @Data
    public static class WidgetConfigDTO {
        private Integer n1, n2, n3, n4, n5;
        private Integer status;

        public List<Integer> getConfig() {
            return List.of(n1, n2, n3, n4, n5);
        }
        public void setConfig(List<Integer> config) {
            this.n1 = config.get(0);
            this.n2 = config.get(1);
            this.n3 = config.get(2);
            this.n4 = config.get(3);
            this.n5 = config.get(4);
        }
    }

    @Data
    public static class StatBlockDTO {
        private Integer type;
        private Integer amount;
        private Integer time;
        private Integer size;

        public List<Integer> getBlock() {
            return List.of(type, amount, time, size);
        }
        public void setBlock(List<Integer> config) {
            this.type = config.get(0);
            this.amount = config.get(1);
            this.time = config.get(2);
            this.size = config.get(3);
        }
    }
}
