package org.iset.emplois.repository;
 
import java.util.List;

import org.iset.emplois.model.ECUE; 
import org.iset.emplois.model.Matiere;
import org.iset.emplois.model.Parcours;
import org.iset.emplois.model.UE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ECUERepository extends JpaRepository<ECUE, Long> {
 Page<ECUE> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT d FROM ECUE d WHERE " +
           "(:nommatfr IS NULL OR LOWER(d.matiere.nommatfr) LIKE LOWER(CONCAT('%', :nommatfr, '%'))) AND " +
           "(:nomparcoursfr IS NULL OR LOWER(d.parcours.nomparcoursfr) LIKE LOWER(CONCAT('%', :nomparcoursfr, '%')))")
    List<ECUE> findByNomUEOrDescription(@Param("nommatfr") String nommatfr, @Param("nomparcoursfr") String nomparcoursfr);


    @Query("SELECT d FROM ECUE d WHERE " +
           "(:nommatfr IS NULL OR LOWER(d.matiere.nommatfr) LIKE LOWER(CONCAT('%', :nommatfr, '%'))) AND " +
           "(:nomparcoursfr IS NULL OR LOWER(d.parcours.nomparcoursfr) LIKE LOWER(CONCAT('%', :nomparcoursfr, '%')))")
    Page<ECUE> searchECUEs(
            @Param("nommatfr") String nommatfr,
            @Param("nomparcoursfr") String nomparcoursfr,
            Pageable pageable);


            // Trouver les matières par l'ID de l'UE
    @Query("SELECT DISTINCT e.matiere FROM ECUE e WHERE e.ue.id = :ueId")
    List<Matiere> findMatieresByUEId(Long ueId);

    // Trouver les UEs par l'ID du parcours
    @Query("SELECT DISTINCT e.ue FROM ECUE e WHERE e.parcours.id = :parcoursId")
    List<UE> findUEsByParcoursId(Long parcoursId);

    // Trouver les parcours par l'ID du plan d'étude
    @Query("SELECT DISTINCT e.parcours FROM ECUE e WHERE e.planEtude.id = :planEtudeId")
    List<Parcours> findParcoursByPlanEtudeId(Long planEtudeId);

 // Trouver les niveaux par l'ID du plan d'étude
    @Query("SELECT DISTINCT e.niveau FROM ECUE e WHERE e.planEtude.id = :planId ORDER BY e.niveau")
List<Integer> findDistinctNiveauxByPlanEtudeId(@Param("planId") Long planId);

@Query("""
    SELECT e 
    FROM ECUE e 
    WHERE e.planEtude.id = (
        SELECT g.detailsAU.planetude.id 
        FROM GroupeAU g 
        WHERE g.id = :groupeauId
    )
    AND e.niveau = (
        SELECT g.detailsAU.niveau FROM GroupeAU g WHERE g.id = :groupeauId
    )
    AND e.semestre = (
        SELECT g.semestre FROM GroupeAU g WHERE g.id = :groupeauId
    )
""")
 
List<ECUE> findByGroupeAUId(@Param("groupeauId") Long groupeauId); 


}
   
 
