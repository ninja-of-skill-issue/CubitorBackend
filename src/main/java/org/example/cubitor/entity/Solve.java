package org.example.cubitor.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString
public class Solve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    private String tim;
    private String scramble;
    private String creation_date;
    private String note;
    private Integer penalty;

    @ManyToOne
    private Event event;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Folder folder;
}
