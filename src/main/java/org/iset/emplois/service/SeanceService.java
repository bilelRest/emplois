package org.iset.emplois.service;
 
import java.util.List;
import java.util.Optional;

import org.iset.emplois.model.Seance;
import org.iset.emplois.repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SeanceService {
     @Autowired
    private SeanceRepository seanceRepository;


    

    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    public Seance getSeanceById(Long id) {
        Optional<Seance> seance = seanceRepository.findById(id);
        return seance.orElse(null);
    }

    public void addSeance(Seance seance) {
        seanceRepository.save(seance);
    }

    public void updateSeance(Seance seance) {
        seanceRepository.save(seance);
    }

    public void deleteSeance(Long id) {
        seanceRepository.deleteById(id);
    }
}
