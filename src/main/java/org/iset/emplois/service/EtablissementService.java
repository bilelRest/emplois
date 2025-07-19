package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;  
import org.iset.emplois.model.Etablissement; 
import org.iset.emplois.repository.EtablissementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EtablissementService {
private final EtablissementRepository  repository;

    public EtablissementService(EtablissementRepository repository) {
        this.repository = repository;
    }
 public Page<Etablissement> findEtablissements(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<Etablissement> findAll() {
        return repository.findAll();
    }
    public Page<Etablissement> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Etablissement> findById(Long id) {
        return repository.findById(id);
    }

    public Etablissement save(Etablissement creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<Etablissement> searchEtablissements(String nomdepfr, String nomdepar) {
        return repository.findByNometabfrOrNometabar(nomdepfr, nomdepar);
    }


    public Page<Etablissement> searchEtablissements(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchEtablissements(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
