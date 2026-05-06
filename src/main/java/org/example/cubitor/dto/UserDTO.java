package org.example.cubitor.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Integer elo;
    private String lastOnline;
    private String creationDate;
    private String profilePic;

    // id's
    private List<Long> friends;
    private List<Long> solveIDs;
    private List<Long> folderIDs;
    private long settingsID;
    private long roleID;

}

