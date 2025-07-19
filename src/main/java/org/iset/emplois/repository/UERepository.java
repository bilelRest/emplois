package org.iset.emplois.repository;
 
import java.util.List;

import org.iset.emplois.model.Parcours;
import org.iset.emplois.model.UE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UERepository extends JpaRepository<UE, Long> {
 Page<UE> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT d FROM UE d WHERE " +
           "(:nomUE IS NULL OR LOWER(d.nomUE) LIKE LOWER(CONCAT('%', :nomUE, '%'))) AND " +
           "(:descriptionUE IS NULL OR LOWER(d.descriptionUE) LIKE LOWER(CONCAT('%', :descriptionUE, '%')))")
    List<UE> findByNomUEOrDescription(@Param("nomUE") String nomUE, @Param("descriptionUE") String descriptionUE);


    @Query("SELECT d FROM UE d WHERE " +
           "(:nomUE IS NULL OR LOWER(d.nomUE) LIKE LOWER(CONCAT('%', :nomUE, '%'))) AND " +
           "(:descriptionUE IS NULL OR LOWER(d.descriptionUE) LIKE LOWER(CONCAT('%', :descriptionUE, '%')))")
    Page<UE> searchUEs(
            @Param("nomUE") String nomUE,
            @Param("descriptionUE") String descriptionUE,
            Pageable pageable);


            
      //      @Query("SELECT p FROM Parcours p JOIN PlanEtude pe ON p.id = pe.parcours.id WHERE pe.id = :planEtudeId")
//List<Parcours> findUEByParcoursId(@Param("parcoursId") Long parcoursId);
}
   
 
