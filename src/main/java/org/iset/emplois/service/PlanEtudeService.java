package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;    
import org.iset.emplois.model.PlanEtude; 
import org.iset.emplois.repository.PlanEtudeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlanEtudeService {
private final PlanEtudeRepository  repository;

    public PlanEtudeService(PlanEtudeRepository repository) {
        this.repository = repository;
    }
 public Page<PlanEtude> findPlanEtudes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<PlanEtude> findAll() {
        return repository.findAll();
    }
    public Page<PlanEtude> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<PlanEtude> findById(Long id) {
        return repository.findById(id);
    }

    public PlanEtude save(PlanEtude creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<PlanEtude> searchPlanEtudes(String nomdepfr, String nomdepar) {
        return repository.findByNomfrOrDescription(nomdepfr, nomdepar);
    }


    public Page<PlanEtude > searchPlanEtudes(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchPlanEtudes(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
