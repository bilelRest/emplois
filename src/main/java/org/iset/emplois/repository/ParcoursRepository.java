package org.iset.emplois.repository;
 
import java.util.List;
  
import org.iset.emplois.model.Parcours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParcoursRepository extends JpaRepository<Parcours, Long> {
 Page<Parcours> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT d FROM Parcours d WHERE " +
           "(:nomparcoursfr IS NULL OR LOWER(d.nomparcoursfr) LIKE LOWER(CONCAT('%', :nomparcoursfr, '%'))) AND " +
           "(:nomparcoursar IS NULL OR LOWER(d.nomparcoursar) LIKE LOWER(CONCAT('%', :nomparcoursar, '%')))")
    List<Parcours> findByNomfrOrNomar(@Param("nomparcoursfr") String nomparcoursfr, @Param("nomparcoursar") String nomparcoursar);


    @Query("SELECT d FROM Parcours d WHERE " +
           "(:nomparcoursfr IS NULL OR LOWER(d.nomparcoursfr) LIKE LOWER(CONCAT('%', :nomparcoursfr, '%'))) AND " +
           "(:nomparcoursar IS NULL OR LOWER(d.nomparcoursar) LIKE LOWER(CONCAT('%', :nomparcoursar, '%')))")
    Page<Parcours> searchParcours(
            @Param("nomparcoursfr") String nomparcoursfr,
            @Param("nomparcoursar") String nomparcoursar,
            Pageable pageable);


        @Query("SELECT p FROM Parcours p JOIN PlanEtude pe ON p.id = pe.parcours.id WHERE pe.id = :planEtudeId")
List<Parcours> findParcoursByPlanEtudeId(@Param("planEtudeId") Long planEtudeId);
}
   
 
