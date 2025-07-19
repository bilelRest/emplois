package org.iset.emplois.repository;
 
import java.util.List;

import org.iset.emplois.model.Departement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
 Page<Departement> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT d FROM Departement d WHERE " +
           "(:nomdepfr IS NULL OR LOWER(d.nomdepfr) LIKE LOWER(CONCAT('%', :nomdepfr, '%'))) AND " +
           "(:nomdepar IS NULL OR LOWER(d.nomdepar) LIKE LOWER(CONCAT('%', :nomdepar, '%')))")
    List<Departement> findByNomdepfrOrNomdepar(@Param("nomdepfr") String nomdepfr, @Param("nomdepar") String nomdepar);


    @Query("SELECT d FROM Departement d WHERE " +
           "(:nomdepfr IS NULL OR LOWER(d.nomdepfr) LIKE LOWER(CONCAT('%', :nomdepfr, '%'))) AND " +
           "(:nomdepar IS NULL OR LOWER(d.nomdepar) LIKE LOWER(CONCAT('%', :nomdepar, '%')))")
    Page<Departement> searchDepartements(
            @Param("nomdepfr") String nomdepfr,
            @Param("nomdepar") String nomdepar,
            Pageable pageable);
}
   
 
