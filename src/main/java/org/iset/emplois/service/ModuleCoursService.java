package org.iset.emplois.service;

import java.util.List;
import java.util.Optional; 
import org.iset.emplois.model.ModuleCours;  
import org.iset.emplois.repository.ModuleCoursRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ModuleCoursService {
private final ModuleCoursRepository  repository;

    public ModuleCoursService(ModuleCoursRepository repository) {
        this.repository = repository;
    }
 public Page<ModuleCours> findModuleCours(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<ModuleCours> findAll() {
        return repository.findAll();
    }
    public Page<ModuleCours> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<ModuleCours> findById(Long id) {
        return repository.findById(id);
    }

    public ModuleCours save(ModuleCours creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<ModuleCours> searchModuleCours(String nomdepfr, String nomdepar) {
        return repository.findByNomModulefrOrNomModulear(nomdepfr, nomdepar);
    }


    public Page<ModuleCours> searchModuleCours(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchModuleCours(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
