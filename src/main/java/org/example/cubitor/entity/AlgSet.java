package org.example.cubitor.entity;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "ALG_SET")
public class AlgSet extends AlgCollection { }
