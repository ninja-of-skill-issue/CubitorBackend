package org.example.cubitor.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorColumn(name = "FOLDER")
public class Folder extends AlgCollection {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Solve> solves;
}

