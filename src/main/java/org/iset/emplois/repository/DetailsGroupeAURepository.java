package org.iset.emplois.repository;
 
import java.util.List;

import org.iset.emplois.model.DetailsAU;
import org.iset.emplois.model.DetailsGroupeAU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DetailsGroupeAURepository extends JpaRepository<DetailsGroupeAU, Long> {
 Page<DetailsGroupeAU> findAll(Pageable pageable);

  // Méthode de recherche par Groupenomdepfr ou nomdepar
    @Query("SELECT d FROM DetailsGroupeAU d WHERE " +
           "(:nomgroupe IS NULL OR LOWER(d.groupeAU.nomgroupe) LIKE LOWER(CONCAT('%', :nomgroupe, '%'))) AND " +
           "(:libelleFr IS NULL OR LOWER(d.creneau.libelleFr) LIKE LOWER(CONCAT('%', :libelleFr, '%')))")
    List<DetailsGroupeAU> findByNomAbrOrParcours(@Param("nomgroupe") String nomabr, @Param("libelleFr") String parcours);
  

    @Query("SELECT d FROM DetailsGroupeAU d WHERE " +
           "(:nomgroupe IS NULL OR LOWER(d.groupeAU.nomgroupe) LIKE LOWER(CONCAT('%', :nomgroupe, '%'))) AND " +
           "(:libelleFr IS NULL OR LOWER(d.creneau.libelleFr) LIKE LOWER(CONCAT('%', :libelleFr, '%')))")
    Page<DetailsGroupeAU> searchDetailsGroupeAUs(
            @Param("nomgroupe") String nomabr,
            @Param("libelleFr") String libelleFr,
            Pageable pageable);
}
   
 
