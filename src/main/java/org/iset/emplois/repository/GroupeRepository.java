package org.iset.emplois.repository;
 
import java.util.List;

import org.iset.emplois.model.Groupe; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupeRepository extends JpaRepository<Groupe, Long> {
 
Page<Groupe> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT d FROM Groupe d WHERE " +
           "(:nomgroupe IS NULL OR LOWER(d.nomgroupe) LIKE LOWER(CONCAT('%', :nomgroupe, '%'))) AND " +
           "(:nomparcoursfr IS NULL OR LOWER(d.parcours.nomparcoursfr) LIKE LOWER(CONCAT('%', :nomparcoursfr, '%')))")
    List<Groupe> findByNomgroupeOrNomparcoursfr(@Param("nomgroupe") String nomgroupe, @Param("nomparcoursfr") String nomparcoursfr);


    @Query("SELECT d FROM Groupe d WHERE " +
           "(:nomgroupe IS NULL OR LOWER(d.nomgroupe) LIKE LOWER(CONCAT('%', :nomgroupe, '%'))) AND " +
           "(:nomparcoursfr IS NULL OR LOWER(d.parcours.nomparcoursfr) LIKE LOWER(CONCAT('%', :nomparcoursfr, '%')))")
    Page<Groupe> searchGroupes(
            @Param("nomgroupe") String nomgroupe,
            @Param("nomparcoursfr") String nomparcoursfr,
            Pageable pageable);
}
   
 
