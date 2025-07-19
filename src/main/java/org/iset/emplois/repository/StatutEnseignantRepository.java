package org.iset.emplois.repository;
 
import java.util.List;
  
import org.iset.emplois.model.StatutEnseignant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatutEnseignantRepository extends JpaRepository<StatutEnseignant, Long> {

       Page<StatutEnseignant> findAll(Pageable pageable);

 
    @Query("SELECT m FROM StatutEnseignant m WHERE " +
           "(:nomstatusfr IS NULL OR LOWER(m.nomstatusfr) LIKE LOWER(CONCAT('%', :nomstatusfr, '%'))) AND " +
           "(:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    List<StatutEnseignant> findByNomfrOrDescription(@Param("nomstatusfr") String nomstatusfr, @Param("description") String description);


    @Query("SELECT m FROM StatutEnseignant m WHERE " +
           "(:nomstatusfr IS NULL OR LOWER(m.nomstatusfr) LIKE LOWER(CONCAT('%', :nomstatusfr, '%'))) AND " +
           "(:prenom IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    Page<StatutEnseignant> searchStatutEnseignants(
            @Param("nomstatusfr") String nomstatusfr,
            @Param("description") String description,
            Pageable pageable);
}
