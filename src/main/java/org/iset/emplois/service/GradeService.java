package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;   
import org.iset.emplois.model.Grade; 
import org.iset.emplois.repository.GradeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GradeService {
private final GradeRepository  repository;

    public GradeService(GradeRepository repository) {
        this.repository = repository;
    }
 public Page<Grade> findGrades(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<Grade> findAll() {
        return repository.findAll();
    }
    public Page<Grade> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Grade> findById(Long id) {
        return repository.findById(id);
    }

    public Grade save(Grade creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<Grade> searchGrades(String nomdepfr, String nomdepar) {
        return repository.findByNomfrOrDescription(nomdepfr, nomdepar);
    }


    public Page<Grade > searchGrades(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchGrades(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
