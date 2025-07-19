package org.iset.emplois.security.repositories;

import java.util.List;
import java.util.Optional;

import org.iset.emplois.security.entities.AppUser;
import org.iset.emplois.security.entities.DroitsSubMenuUser;
import org.iset.emplois.security.entities.SubMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DroitsSubMenuUserRepository extends JpaRepository<DroitsSubMenuUser, Long> {
    List<DroitsSubMenuUser> findByUser(AppUser user);


     /**
     * Recherche un enregistrement de droits pour un utilisateur et un sous-menu donnés.
     *
     * @param user     L'utilisateur pour lequel on recherche les droits.
     * @param subMenu  Le sous-menu pour lequel on recherche les droits.
     * @return L'objet DroitsSubMenuUser correspondant, ou null si aucun droit n'est trouvé.
     */
    DroitsSubMenuUser findByUserAndSubMenu(AppUser user, SubMenu subMenu);


    @Query("SELECT d FROM DroitsSubMenuUser d WHERE d.user.userId = :userId AND d.subMenu.id = :subMenuId")
    Optional<DroitsSubMenuUser> findByUserAndSubMenu(@Param("userId") String userId, @Param("subMenuId") Long subMenuId);

    
}

