package org.iset.emplois.repository;
 
import java.util.List;
 
import org.iset.emplois.model.Etablissement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {
 Page<Etablissement> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT d FROM Etablissement d WHERE " +
           "(:nometabfr IS NULL OR LOWER(d.nometabfr) LIKE LOWER(CONCAT('%', :nometabfr, '%'))) AND " +
           "(:nometabar IS NULL OR LOWER(d.nometabar) LIKE LOWER(CONCAT('%', :nometabar, '%')))")
    List<Etablissement> findByNometabfrOrNometabar(@Param("nometabfr") String nometabfr, @Param("nometabar") String nometabar);


    @Query("SELECT d FROM Etablissement d WHERE " +
           "(:nometabfr IS NULL OR LOWER(d.nometabfr) LIKE LOWER(CONCAT('%', :nometabfr, '%'))) AND " +
           "(:nometabar IS NULL OR LOWER(d.nometabar) LIKE LOWER(CONCAT('%', :nometabar, '%')))")
    Page<Etablissement> searchEtablissements(
            @Param("nometabfr") String nometabfr,
            @Param("nometabar") String nometabar,
            Pageable pageable);
}
   
 
