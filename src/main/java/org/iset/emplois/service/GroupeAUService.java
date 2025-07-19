package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;

import org.iset.emplois.model.GroupeAU;
import org.iset.emplois.repository.GroupeAURepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupeAUService {
private final GroupeAURepository  repository;

    public GroupeAUService(GroupeAURepository repository) {
        this.repository = repository;
    }
 public Page<GroupeAU> findGroupeAUs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<GroupeAU> findAll() {
        return repository.findAll();
    }
    public Page<GroupeAU> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<GroupeAU> findById(Long id) {
        return repository.findById(id);
    }

    public GroupeAU save(GroupeAU creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<GroupeAU> searchGroupeAUs(String nomdepfr, String nomdepar) {
        return repository.findByNomAbrOrParcours(nomdepfr, nomdepar);
    }


    public Page<GroupeAU> searchGroupeAUs(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchGroupeAUs(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }


public List<GroupeAU> findByAnneeAndSemestre(  int annee, int semestre, Long departementId){

    return repository.findByAnneeAndSemestre(annee, semestre,departementId);
 
    }


}
