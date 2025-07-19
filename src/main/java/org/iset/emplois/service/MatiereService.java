package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;
 
import org.iset.emplois.model.Matiere;
import org.iset.emplois.repository.MatiereRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MatiereService {
private final MatiereRepository  repository;

    public MatiereService(MatiereRepository repository) {
        this.repository = repository;
    }
 public Page<Matiere> findmatieres(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
     public List<Matiere> findAll() {
        return repository.findAll();
    }
    public Page<Matiere> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Matiere> findById(Long id) {
        return repository.findById(id);
    }

    public Matiere save(Matiere matiere) {
        
        return repository.save(matiere);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<Matiere> searchDepartements(String nommatfr, String nommatfrabr) {
        return repository.findByNommatfrOrNommatfrabr(nommatfr, nommatfrabr);
    }


    public Page<Matiere> searchMatieres(String nommatfr, String nommatfrabr, Pageable pageable) {

        System.out.println(nommatfrabr + " fr : "+ nommatfr);
        return repository.searchMatieres(
            nommatfr != null ? nommatfr : "", 
            nommatfrabr != null ? nommatfrabr : "", 
                pageable);
    }


    public boolean existsByNommatfr(String nommatfr, Long id) {
        return repository.findByNommatfr(nommatfr)
                         .filter(m -> !m.getId().equals(id))
                         .isPresent();
    }

    public boolean existsByNommatfrabr(String nommatfrabr, Long id) {
        return repository.findByNommatfrabr(nommatfrabr)
                         .filter(m -> !m.getId().equals(id))
                         .isPresent();
    }
     

}
