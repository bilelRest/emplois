package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;   
import org.iset.emplois.model.StatutEnseignant; 
import org.iset.emplois.repository.StatutEnseignantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatutEnseignantService {
private final StatutEnseignantRepository  repository;

    public StatutEnseignantService(StatutEnseignantRepository repository) {
        this.repository = repository;
    }
  public Page<StatutEnseignant> findStatutEnseignants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<StatutEnseignant> findAll() {
        return repository.findAll();
    }
    public Page<StatutEnseignant> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<StatutEnseignant> findById(Long id) {
        return repository.findById(id);
    }

    public StatutEnseignant save(StatutEnseignant creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<StatutEnseignant> searchStatutEnseignants(String nomdepfr, String nomdepar) {
        return repository.findByNomfrOrDescription(nomdepfr, nomdepar);
    }


    public Page<StatutEnseignant> searchStatutEnseignants(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchStatutEnseignants(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
