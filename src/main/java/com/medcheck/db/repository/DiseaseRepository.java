package com.medcheck.db.repository;

import com.medcheck.db.entities.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository< Disease, Long> {
}
