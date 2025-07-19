package org.iset.emplois.security.service;

import java.util.List;
import java.util.Optional;

import org.iset.emplois.security.entities.SubMenu;
import org.iset.emplois.security.repositories.SubMenuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class SubMenuService {
private final SubMenuRepository  repository;
 
   
 public Page<SubMenu> findSubMenus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

     public List<SubMenu> findAll() {
        return repository.findAll();
    }
    public Page<SubMenu> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<SubMenu> findById(Long id) {
        return repository.findById(id);
    }

    public SubMenu save(SubMenu creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<SubMenu> searchSubMenus(String nomdepfr, String nomdepar) {
        return repository.findByTitleOrDescription(nomdepfr, nomdepar);
    }


    public Page<SubMenu > searchSubMenus(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return repository.searchSubMenus(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }

}
