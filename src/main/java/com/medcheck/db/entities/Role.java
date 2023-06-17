package main.java.com.medcheck.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
    @SequenceGenerator(name = "role_gen", sequenceName = "role_seq", allocationSize = 1, initialValue = 2)
    private Long id;

    private String roleName;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH}, mappedBy = "role")
    private List<User> users = new ArrayList<>();
}
