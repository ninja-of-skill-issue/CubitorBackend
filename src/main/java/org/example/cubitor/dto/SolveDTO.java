package org.example.cubitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolveDTO {
    private Long id;
    private Integer time;
    private String scramble;
    private String creationDate;
    private String description;
    private Integer penalty;

    // id's
    private Long userID;
    private Long eventID;
    private Long folderID;
}


