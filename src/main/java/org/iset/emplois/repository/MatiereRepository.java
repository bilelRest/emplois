package org.iset.emplois.repository;
 
import java.util.List;
import java.util.Optional;

import org.iset.emplois.model.Matiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {
 Page<Matiere> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT m FROM Matiere m WHERE " +
           "(:nommatfr IS NULL OR LOWER(m.nommatfr) LIKE LOWER(CONCAT('%', :nommatfr, '%'))) AND " +
           "(:nommatfrabr IS NULL OR LOWER(m.nommatfrabr) LIKE LOWER(CONCAT('%', :nommatfrabr, '%')))")
    List<Matiere> findByNommatfrOrNommatfrabr(@Param("nommatfr") String nommatfr, @Param("nommatfrabr") String nommatfrabr);


    @Query("SELECT m FROM Matiere m WHERE " +
           "(:nommatfr IS NULL OR LOWER(m.nommatfr) LIKE LOWER(CONCAT('%', :nommatfr, '%'))) AND " +
           "(:nommatfrabr IS NULL OR LOWER(m.nommatfrabr) LIKE LOWER(CONCAT('%', :nommatfrabr, '%')))")
    Page<Matiere> searchMatieres(
            @Param("nommatfr") String nommatfr,
            @Param("nommatfrabr") String nommatfrabr,
            Pageable pageable);


              Optional<Matiere> findByNommatfr(String nommatfr);
    Optional<Matiere> findByNommatfrabr(String nommatfrabr);
}
   
 
