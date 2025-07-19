package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;

import org.iset.emplois.model.Groupe; 
import org.iset.emplois.repository.GroupeRepository; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupeService {
private final GroupeRepository  repository;

    public GroupeService(GroupeRepository repository) {
        this.repository = repository;
    }
 public Page<Groupe> findParcours(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<Groupe> findAll() {
        return repository.findAll();
    }
    public Page<Groupe> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Groupe> findById(Long id) {
        return repository.findById(id);
    }

    public Groupe save(Groupe groupe) {
        return repository.save(groupe);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<Groupe> searchEtablissements(String nomdepfr, String nomdepar) {
        return repository.findByNomgroupeOrNomparcoursfr(nomdepfr, nomdepar);
    }


    public Page<Groupe> searchGroupes(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchGroupes(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
