package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;   
import org.iset.emplois.model.Salle; 
import org.iset.emplois.repository.SalleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalleService {
private final SalleRepository  repository;

    public SalleService(SalleRepository repository) {
        this.repository = repository;
    }
 public Page<Salle> findsalles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
     public List<Salle> findAll() {
        return repository.findAll();
    }
    public Page<Salle> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Salle> findById(Long id) {
        return repository.findById(id);
    }

    public Salle save(Salle creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<Salle> searchSalles(String nommatfr, String nommatfrabr) {
        return repository.findByNomsalleOrDescription(nommatfr, nommatfrabr);
    }


    public Page<Salle> searchSalles(String nommatfr, String nommatfrabr, Pageable pageable) {

        System.out.println(nommatfrabr + " fr : "+ nommatfr);
        return repository.searchSalles(
            nommatfr != null ? nommatfr : "", 
            nommatfrabr != null ? nommatfrabr : "", 
                pageable);
    }

}
