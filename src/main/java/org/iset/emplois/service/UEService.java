package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;    
import org.iset.emplois.model.UE; 
import org.iset.emplois.repository.UERepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UEService {
private final UERepository  repository;

    public UEService(UERepository repository) {
        this.repository = repository;
    }
 public Page<UE> findUEs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
     public List<UE> findAll() {
        return repository.findAll();
    }
    public Page<UE> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<UE> findById(Long id) {
        return repository.findById(id);
    }

    public UE save(UE creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<UE> searchUEs(String nom, String description) {
        return repository.findByNomUEOrDescription(nom, description);
    }


    public Page<UE> searchUEs(String nom, String description, Pageable pageable) {

        System.out.println(nom + " fr : "+ description);
        return repository.searchUEs(
            nom != null ? nom : "", 
            description != null ? description : "", 
                pageable);
    }

}
