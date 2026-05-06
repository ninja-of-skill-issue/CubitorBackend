package org.example.cubitor.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FolderDTO {
    private long id;
    private String name;

    private long userID;
    private List<Long> solveIDs;

}
