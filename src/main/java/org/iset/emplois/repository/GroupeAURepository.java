package org.iset.emplois.repository;
 
import java.util.List;
 
import org.iset.emplois.model.GroupeAU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupeAURepository extends JpaRepository<GroupeAU, Long> {
 Page<GroupeAU> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT d FROM GroupeAU d WHERE " +
           "(:nomgroupe IS NULL OR LOWER(d.nomgroupe) LIKE LOWER(CONCAT('%', :nomgroupe, '%'))) AND " +
           "(:parcours IS NULL OR LOWER(d.detailsAU.parcours.nomparcoursfr) LIKE LOWER(CONCAT('%', :parcours, '%')))")
    List<GroupeAU> findByNomAbrOrParcours(@Param("nomgroupe") String nomgroupe, @Param("parcours") String parcours);


    @Query("SELECT d FROM GroupeAU d WHERE " +
           "(:nomgroupe IS NULL OR LOWER(d.nomgroupe) LIKE LOWER(CONCAT('%', :nomgroupe, '%'))) AND " +
           "(:parcours IS NULL OR LOWER(d.detailsAU.parcours.nomparcoursfr) LIKE LOWER(CONCAT('%', :parcours, '%')))")
    Page<GroupeAU> searchGroupeAUs(
            @Param("nomgroupe") String nomgroupe,
            @Param("parcours") String parcours,
            Pageable pageable);

    @Query("SELECT g FROM GroupeAU g WHERE g.detailsAU.au.annee = :annee AND g.semestre = :semestre AND g.detailsAU.parcours.departement.id = :departementId")
    List<GroupeAU> findByAnneeAndSemestre(@Param("annee") int annee, @Param("semestre") int semestre,@Param("departementId") Long departementId);

 
}
   
 
