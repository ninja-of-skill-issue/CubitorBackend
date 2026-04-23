package org.example.cubitor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolveResponse {
    private Long id;
    private String tim;
    private String scramble;
    private String creation_date;
    private String note;
    private Integer penalty; // или что у тебя есть
}
