package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;  
import org.iset.emplois.model.DetailsGroupeAU; 
import org.iset.emplois.repository.DetailsGroupeAURepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DetailsGroupeAUService {
private final DetailsGroupeAURepository  repository;

    public DetailsGroupeAUService(DetailsGroupeAURepository repository) {
        this.repository = repository;
    }
 public Page<DetailsGroupeAU> findDetailsGroupeAUs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<DetailsGroupeAU> findAll() {
        return repository.findAll();
    }
    public Page<DetailsGroupeAU> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<DetailsGroupeAU> findById(Long id) {
        return repository.findById(id);
    }

    public DetailsGroupeAU save(DetailsGroupeAU detailsGroupeAU) {
        return repository.save(detailsGroupeAU);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<DetailsGroupeAU> searchDetailsGroupeAUs(String nomdepfr, String nomdepar) {
        return repository.findByNomAbrOrParcours(nomdepfr, nomdepar);
    }


    public Page<DetailsGroupeAU> searchDetailsGroupeAUs(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchDetailsGroupeAUs(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
