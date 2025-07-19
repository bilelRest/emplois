package org.iset.emplois.repository;
 
import java.util.List;
 
import org.iset.emplois.model.PlanEtude;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanEtudeRepository extends JpaRepository<PlanEtude, Long> {
 
       Page<PlanEtude> findAll(Pageable pageable);

@Query("SELECT p FROM PlanEtude p " +
       "WHERE p.parcours.id = :parcoursId " +
       "AND FUNCTION('YEAR', p.datedebut) <= :annee " +
       "AND FUNCTION('YEAR', p.datefin) >= :annee")
List<PlanEtude> findByParcoursIdAndAnnee(@Param("parcoursId") Long parcoursId,
                                                     @Param("annee") int annee);


    @Query("SELECT m FROM PlanEtude m WHERE " +
           "(:nomplanfr IS NULL OR LOWER(m.nomplanfr) LIKE LOWER(CONCAT('%', :nomplanfr, '%'))) AND " +
           "(:nomplanar IS NULL OR LOWER(m.nomplanar) LIKE LOWER(CONCAT('%', :nomplanar, '%')))")
    List<PlanEtude> findByNomfrOrDescription(@Param("nomplanfr") String nomplanfr, @Param("nomplanar") String nomplanar);


    @Query("SELECT m FROM PlanEtude m WHERE " +
           "(:nomplanfr IS NULL OR LOWER(m.nomplanfr) LIKE LOWER(CONCAT('%', :nomplanfr, '%'))) AND " +
           "(:nomplanar IS NULL OR LOWER(m.nomplanar) LIKE LOWER(CONCAT('%', :nomplanar, '%')))")
    Page<PlanEtude> searchPlanEtudes(
            @Param("nomplanfr") String nomplanfr,
            @Param("nomplanar") String nomplanar,
            Pageable pageable);
}
