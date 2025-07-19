package org.iset.emplois.repository;
 
import java.util.List;

import org.iset.emplois.model.Departement;
import org.iset.emplois.model.Salle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalleRepository extends JpaRepository<Salle, Long> {

       Page<Salle> findAll(Pageable pageable);

 
    @Query("SELECT m FROM Salle m WHERE " +
           "(:nom IS NULL OR LOWER(m.nomsalle) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
           "(:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    List<Salle> findByNomsalleOrDescription(@Param("nom") String nom, @Param("description") String prenom);


    @Query("SELECT m FROM Salle m WHERE " +
           "(:nom IS NULL OR LOWER(m.nomsalle) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
           "(:prenom IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    Page<Salle> searchSalles(
            @Param("nom") String nom,
            @Param("description") String prenom,
            Pageable pageable);


            @Query("SELECT DISTINCT s FROM Salle s JOIN s.departements d WHERE d IN :departements")
List<Salle> findByDepartementsIn(@Param("departements") List<Departement> departements);
}
