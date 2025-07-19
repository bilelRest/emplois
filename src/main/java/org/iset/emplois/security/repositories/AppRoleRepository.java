package org.iset.emplois.security.repositories;

import java.util.List;
import java.util.Optional; 
import org.iset.emplois.security.entities.AppRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
 


@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole>  findByRoleName(String roleName);//(String rolename);


          Page<AppRole> findAll(Pageable pageable);

 
    @Query("SELECT m FROM AppRole m WHERE " +
           "(:roleName IS NULL OR LOWER(m.roleName) LIKE LOWER(CONCAT('%', :roleName, '%'))) AND " +
           "(:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    List<AppRole> findByNomOrDescription(@Param("roleName") String nomgradefr, @Param("description") String description);


    @Query("SELECT m FROM AppRole m WHERE " +
           "(:roleName IS NULL OR LOWER(m.roleName) LIKE LOWER(CONCAT('%', :roleName, '%'))) AND " +
           "(:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    Page<AppRole> searchRoles(
            @Param("roleName") String roleName,
            @Param("description") String description,
            Pageable pageable);
}

