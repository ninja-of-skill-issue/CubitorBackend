package org.example.cubitor.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SolvesByUserResponse {
    private List<SolveResponse> solves;
}