package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;   
import org.iset.emplois.model.Parcours; 
import org.iset.emplois.repository.ParcoursRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParcoursService {
private final ParcoursRepository  repository;

    public ParcoursService(ParcoursRepository repository) {
        this.repository = repository;
    }
 public Page<Parcours> findParcours(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<Parcours> findAll() {
        return repository.findAll();
    }
    public Page<Parcours> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Parcours> findById(Long id) {
        return repository.findById(id);
    }

    public Parcours save(Parcours creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<Parcours> searchEtablissements(String nomdepfr, String nomdepar) {
        return repository.findByNomfrOrNomar(nomdepfr, nomdepar);
    }


 

 public List<Parcours> findParcoursByPlanEtudeId(Long planEtudeId) {
        return repository.findParcoursByPlanEtudeId(planEtudeId);
    }

    public Page<Parcours> searchParcours(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchParcours(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
