package main.java.com.medcheck.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity

public class Patient extends User{
    private String description;
    @OneToMany()
    private List<Disease> diseaseHistory;

}
