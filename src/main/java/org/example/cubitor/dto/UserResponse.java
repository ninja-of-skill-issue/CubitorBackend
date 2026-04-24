package org.example.cubitor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private String role;
    private String token;
    private String elo;
    private String last_online;
    private String avatar;
    private String description;
    private String account_creation_date;
    private String friends;
}
