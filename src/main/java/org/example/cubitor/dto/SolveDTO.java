package org.example.cubitor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolveDTO {
    private Long id;
    private int time;
    private String scramble;
    private String creationDate;
    private String description;
    private int penalty;

    // id's
    private Long userID;
    private Long eventID;
    private Long folderID;
}

