package org.iset.emplois.service;

import java.util.List;
import java.util.Optional; 
import org.iset.emplois.model.Departement; 
import org.iset.emplois.repository.DepartementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepartementService {
private final DepartementRepository  repository;

    public DepartementService(DepartementRepository repository) {
        this.repository = repository;
    }
 public Page<Departement> findDepartements(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<Departement> findAll() {
        return repository.findAll();
    }
    public Page<Departement> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Departement> findById(Long id) {
        return repository.findById(id);
    }

    public Departement save(Departement creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<Departement> searchDepartements(String nomdepfr, String nomdepar) {
        return repository.findByNomdepfrOrNomdepar(nomdepfr, nomdepar);
    }


    public Page<Departement> searchDepartements(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchDepartements(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
