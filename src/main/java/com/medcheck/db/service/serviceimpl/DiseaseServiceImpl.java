package com.medcheck.db.service.serviceimpl;

import com.medcheck.db.entities.Disease;
import com.medcheck.db.repository.DiseaseRepository;
import com.medcheck.db.service.DiseaseService;
import com.medcheck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {
    private final DiseaseRepository diseaseRepository;
    public List<Disease> getAllDisease(){
        return diseaseRepository.findAll();
    };
    public Disease getDiseaseById(Long id)  {
       return diseaseRepository.findById(id).orElseThrow(NotFoundException::new);
    }
    public void deleteDisease(Long id){
        diseaseRepository.deleteById(id);
    }
}
