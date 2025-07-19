package org.iset.emplois.security.repositories;
 
import java.util.List;

import org.iset.emplois.security.entities.SubMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubMenuRepository extends JpaRepository<SubMenu, Long> {
   Page<SubMenu> findAll(Pageable pageable);

 
    @Query("SELECT m FROM SubMenu m WHERE " +
           "(:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    List<SubMenu> findByTitleOrDescription(@Param("title") String title, @Param("description") String description);


    @Query("SELECT m FROM SubMenu m WHERE " +
           "(:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    Page<SubMenu> searchSubMenus(
            @Param("title") String title,
            @Param("description") String description,
            Pageable pageable);
}
