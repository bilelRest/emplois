package org.iset.emplois.service;

import java.util.List;
import java.util.Optional; 
import org.iset.emplois.model.AU; 
import org.iset.emplois.repository.AURepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AUService {
private final AURepository  repository;

    public AUService(AURepository repository) {
        this.repository = repository;
    }
 public Page<AU> findAUs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<AU> findAll() {
        return repository.findAll();
    }
    public Page<AU> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<AU> findById(Long id) {
        return repository.findById(id);
    }

    public AU save(AU creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<AU> searchAUs(String nomdepfr, String nomdepar) {
        return repository.findByNomaufrOrNomauar(nomdepfr, nomdepar);
    }


    public Page<AU> searchAUs(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchaus(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
