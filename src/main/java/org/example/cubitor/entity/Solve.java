package org.example.cubitor.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Solve {
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER )
    private Event event;
    private String tim;
    private String scramble;
    private String creation_date;
    private String note;
    private Integer penalty;


}
