package org.iset.emplois.security.repositories;
 
import java.util.List;

import org.iset.emplois.dto.MenuSubMenuDTO; 
import org.iset.emplois.security.entities.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m FROM Menu m JOIN FETCH m.subMenus")
    List<Menu> findAllWithSubMenus();



    @Query("SELECT new org.iset.emplois.dto.MenuSubMenuDTO(sm.id, m.title, sm.title,true,true,true,true,true) " +
           "FROM Menu m " +
           "JOIN m.subMenus sm")
    List<MenuSubMenuDTO> findSubMenusWithTitles();


    @Query("""
    SELECT new org.iset.emplois.dto.MenuSubMenuDTO(
        sm.id, 
        m.title, 
        sm.title, 
        COALESCE(dsmu.canAdd, true), 
        COALESCE(dsmu.canUpdate, true), 
        COALESCE(dsmu.canSearch, true), 
        COALESCE(dsmu.canDelete, true), 
        COALESCE(dsmu.canPrint, true)
    )
    FROM Menu m
    JOIN m.subMenus sm
    LEFT JOIN DroitsSubMenuUser dsmu 
           ON dsmu.subMenu.id = sm.id 
           AND dsmu.user.id = :userId
    """)
List<MenuSubMenuDTO> findSubMenusWithTitlesAndUserRights(@Param("userId")
 String userId);
 @Query("""
    SELECT new org.iset.emplois.dto.MenuSubMenuDTO(sm.id, m.title, sm.title, true, true, true, true, true)
    FROM Menu m
    JOIN m.subMenus sm
    """)
List<MenuSubMenuDTO> findSubMenusWithDefaults();


Page<Menu> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT d FROM Menu d WHERE " +
           "(:title IS NULL OR LOWER(d.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:description IS NULL OR LOWER(d.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    List<Menu> findByTitleOrDescription(@Param("title") String title, @Param("description") String description);


    @Query("SELECT d FROM Menu d WHERE " +
           "(:title IS NULL OR LOWER(d.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:nomdepar IS NULL OR LOWER(d.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    Page<Menu> searchMenus(
            @Param("title") String title,
            @Param("description") String description,
            Pageable pageable);


  //          @Query("SELECT m FROM Menu m JOIN m.appRoles r WHERE r.roleName = :roleName")
   // List<Menu> findMenusByRoleName(@Param("roleName") String roleName);
}
