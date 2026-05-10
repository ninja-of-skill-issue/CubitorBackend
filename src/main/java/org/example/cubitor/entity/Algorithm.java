package org.example.cubitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Algorithm {
    @Id
    private Long id;
    private String name;
    private String algorithm;

    @ManyToOne private Event event;
    @ManyToMany private List<Folder> folders;
    @ManyToOne private User user;
    @ManyToOne private AlgSet algSet;
}
