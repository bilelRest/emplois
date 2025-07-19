package org.iset.emplois.security.service;

import java.util.List;
import java.util.Optional;

import org.iset.emplois.dto.MenuSubMenuDTO; 
import org.iset.emplois.security.entities.Menu;
import org.iset.emplois.security.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public List<Menu> getAllMenusWithSubMenus() {
        // Récupère tous les menus et leurs sous-menus grâce à une requête optimisée (fetch)
        return menuRepository.findAllWithSubMenus();
    }

    public List<MenuSubMenuDTO> findSubMenusWithTitles(){
        return menuRepository.findSubMenusWithTitles();
    }

    public List<MenuSubMenuDTO> findSubMenusWithDefaults(){
        return menuRepository.findSubMenusWithDefaults();
    }
 

 public List<MenuSubMenuDTO> findSubMenusWithTitlesAndUserRights(String userId){
    return menuRepository.findSubMenusWithTitlesAndUserRights(  userId);
}



public Page<Menu> findMenus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return menuRepository.findAll(pageable);
    }

     public List<Menu> findAll() {
        return menuRepository.findAll();
    }
    public Page<Menu> findAll(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id);
    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }


    public List<Menu> searchMenus(String nomdepfr, String nomdepar) {
        return menuRepository.findByTitleOrDescription(nomdepfr, nomdepar);
    }


    public Page<Menu> searchMenus(String nomdepfr, String nomdepar, Pageable pageable) {

        System.out.println(nomdepar + " fr : "+ nomdepfr);
        return menuRepository.searchMenus(
                nomdepfr != null ? nomdepfr : "", 
                nomdepar != null ? nomdepar : "", 
                pageable);
    }


    
  //  public List<Menu> getMenusByRole(String roleName) {
  //      return menuRepository.findMenusByRoleName(roleName);
   // }

}

