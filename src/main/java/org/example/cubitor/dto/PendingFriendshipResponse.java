package org.example.cubitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingFriendshipResponse {
    private UserResponse user;
    private UserResponse destination;
}
