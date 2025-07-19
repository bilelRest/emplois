package org.iset.emplois.security.service;

import java.util.List;
import java.util.Optional;    
import org.iset.emplois.security.entities.AppRole;
import org.iset.emplois.security.repositories.AppRoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppRoleService {
private final AppRoleRepository  repository;





    public AppRoleService(AppRoleRepository repository) {
        this.repository = repository;
    }

  /**
     * Récupère tous les rôles disponibles dans la base de données.
     *
     * @return une liste de rôles (AppRole)
     */
    public List<AppRole> getAllRoles() {
        return repository.findAll();
    }

 public Page<AppRole> findRoles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<AppRole> findAll() {
        return repository.findAll();
    }
    public Page<AppRole> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<AppRole> findById(Long id) {
        return repository.findById(id);
    }

    public AppRole save(AppRole creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<AppRole> searchRoles(String nomdepfr, String nomdepar) {
        return repository.findByNomOrDescription(nomdepfr, nomdepar);
    }


    public Page<AppRole > searchRoles(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchRoles(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
