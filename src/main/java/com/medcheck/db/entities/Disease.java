package com.medcheck.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "disease")
public class Disease {
    @Id
    @SequenceGenerator(name = "disease_gen", sequenceName = "disease_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disease_gen")
    private Long id;
    private String title;
    private LocalDate startedAt;
    private String description;
}
