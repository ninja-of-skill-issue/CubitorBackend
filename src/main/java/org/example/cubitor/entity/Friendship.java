package org.example.cubitor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "friendships")
@Data
@NoArgsConstructor
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    @JsonIgnore
    private User friend;

    @ColumnDefault("false")
    private Boolean userAccepted;

    @ColumnDefault("false")
    private Boolean friendAccepted;


    public User getMyFriend(User me) {
        return this.user.equals(me) ? this.friend : this.user;
    }
}

