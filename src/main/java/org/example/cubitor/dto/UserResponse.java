package org.example.cubitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cubitor.entity.Friendship;
import org.example.cubitor.entity.User;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private List<Friendship> friends;
}
