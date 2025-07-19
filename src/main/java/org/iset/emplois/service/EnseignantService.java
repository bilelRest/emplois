package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;

import org.iset.emplois.model.Enseignant; 
import org.iset.emplois.repository.EnseignantRepository; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EnseignantService {
private final EnseignantRepository  repository;

    public EnseignantService(EnseignantRepository repository) {
        this.repository = repository;
    }
 public Page<Enseignant> findenseignants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
     public List<Enseignant> findAll() {
        return repository.findAll();
    }
    public Page<Enseignant> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Enseignant> findById(Long id) {
        return repository.findById(id);
    }

    public Enseignant save(Enseignant creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<Enseignant> searchEnseignants(String nommatfr, String nommatfrabr) {
        return repository.findByNomOrPrenom(nommatfr, nommatfrabr);
    }


    public Page<Enseignant> searchEnseignants(String nommatfr, String nommatfrabr, Pageable pageable) {

        System.out.println(nommatfrabr + " fr : "+ nommatfr);
        return repository.searchEnseignants(
            nommatfr != null ? nommatfr : "", 
            nommatfrabr != null ? nommatfrabr : "", 
                pageable);
    }

}
