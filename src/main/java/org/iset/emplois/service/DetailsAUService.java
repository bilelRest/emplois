package org.iset.emplois.service;

import java.util.List;
import java.util.Optional; 
import org.iset.emplois.model.DetailsAU; 
import org.iset.emplois.repository.DetailsAURepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DetailsAUService {
private final DetailsAURepository  repository;

    public DetailsAUService(DetailsAURepository repository) {
        this.repository = repository;
    }
 public Page<DetailsAU> findDetailsAUs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<DetailsAU> findAll() {
        return repository.findAll();
    }
    public Page<DetailsAU> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<DetailsAU> findById(Long id) {
        return repository.findById(id);
    }

    public DetailsAU save(DetailsAU creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<DetailsAU> searchDetailsAUs(String nomdepfr, String nomdepar) {
        return repository.findByNomAbrOrParcours(nomdepfr, nomdepar);
    }


    public Page<DetailsAU> searchDetailsAUs(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchDetailsAUs(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
