package org.iset.emplois.repository;
 
import java.util.List;

import org.iset.emplois.model.DetailsAU; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DetailsAURepository extends JpaRepository<DetailsAU, Long> {
 Page<DetailsAU> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT d FROM DetailsAU d WHERE " +
           "(:nomabr IS NULL OR LOWER(d.nomabr) LIKE LOWER(CONCAT('%', :nomabr, '%'))) AND " +
           "(:parcours IS NULL OR LOWER(d.parcours.nomparcoursfr) LIKE LOWER(CONCAT('%', :parcours, '%')))")
    List<DetailsAU> findByNomAbrOrParcours(@Param("nomabr") String nomabr, @Param("parcours") String parcours);


    @Query("SELECT d FROM DetailsAU d WHERE " +
           "(:nomabr IS NULL OR LOWER(d.nomabr) LIKE LOWER(CONCAT('%', :nomabr, '%'))) AND " +
           "(:parcours IS NULL OR LOWER(d.parcours.nomparcoursfr) LIKE LOWER(CONCAT('%', :parcours, '%')))")
    Page<DetailsAU> searchDetailsAUs(
            @Param("nomabr") String nomabr,
            @Param("parcours") String parcours,
            Pageable pageable);


           
}
   
 
