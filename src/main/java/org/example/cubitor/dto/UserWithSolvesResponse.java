package org.example.cubitor.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserWithSolvesResponse {
    private Long id;
    private String email;
    private String username;
    private String role;
    private String token;
    private String creation_date;
    private String elo;
    private String last_online;
    private String avatar;
    private String description;
    private String accaunt_creation_date;
    private String friends;
    private List<SolveResponse> solves;
}