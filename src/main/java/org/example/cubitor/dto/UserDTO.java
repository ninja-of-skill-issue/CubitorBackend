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
    private Long settingsID;
    private Integer roleID;

}

