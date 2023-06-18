package main.java.com.medcheck.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Disease {
    @Id
    private Long id;
    private String title;
    private LocalDate startedAt;
    private String description;
}
