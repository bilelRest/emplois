package org.iset.emplois.repository;
 
import java.util.List;

import org.iset.emplois.model.AU; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AURepository extends JpaRepository<AU, Long> {
 Page<AU> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT d FROM AU d WHERE " +
           "(:nomaufr IS NULL OR LOWER(d.nomaufr) LIKE LOWER(CONCAT('%', :nomaufr, '%'))) AND " +
           "(:nomauar IS NULL OR LOWER(d.nomauar) LIKE LOWER(CONCAT('%', :nomauar, '%')))")
    List<AU> findByNomaufrOrNomauar(@Param("nomaufr") String nomaufr, @Param("nomauar") String nomauar);


    @Query("SELECT d FROM AU d WHERE " +
           "(:nomaufr IS NULL OR LOWER(d.nomaufr) LIKE LOWER(CONCAT('%', :nomaufr, '%'))) AND " +
           "(:nomauar IS NULL OR LOWER(d.nomauar) LIKE LOWER(CONCAT('%', :nomauar, '%')))")
    Page<AU> searchaus(
            @Param("nomaufr") String nomaufr,
            @Param("nomauar") String nomauar,
            Pageable pageable);
}
   
 
