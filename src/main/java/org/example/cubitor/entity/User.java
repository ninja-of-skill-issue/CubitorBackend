package org.example.cubitor.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Integer elo;
    private String last_online;
    private String account_creation_date;
    @Column(columnDefinition = "LONGTEXT")
    private String avatar;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solve> solves;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> folders;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Settings settings;

    // --- Friends ---
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Friendship> friendshipsSent = new ArrayList<>();
    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Friendship> friendshipsReceived = new ArrayList<>();

    public List<Friendship> getFriendships() {
        List<Friendship> all = new ArrayList<>();
        if (friendshipsSent != null) all.addAll(friendshipsSent);
        if (friendshipsReceived != null) all.addAll(friendshipsReceived);
        return all;
    }
    public void setFriendships(List<Friendship> friendships) {
        this.friendshipsSent = friendships;
    }

    public String getTheActualUsername() {
        return username;
    }
    public void setTheActualUsername(String username) {
        this.username = username;
    }

    // --- Spring Security ---

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public @NonNull String getUsername() {
        return email;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}