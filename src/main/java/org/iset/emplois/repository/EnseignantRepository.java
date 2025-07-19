package org.iset.emplois.repository;
 
import java.util.List;

import org.iset.emplois.model.Enseignant; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
 
 
       Page<Enseignant> findAll(Pageable pageable);

 
    @Query("SELECT m FROM Enseignant m WHERE " +
           "(:nom IS NULL OR LOWER(m.nomenseignant) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
           "(:prenom IS NULL OR LOWER(m.prenomenseignant) LIKE LOWER(CONCAT('%', :prenom, '%')))")
    List<Enseignant> findByNomOrPrenom(@Param("nom") String nom, @Param("prenom") String prenom);


    @Query("SELECT m FROM Enseignant m WHERE " +
           "(:nom IS NULL OR LOWER(m.nomenseignant) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
           "(:prenom IS NULL OR LOWER(m.prenomenseignant) LIKE LOWER(CONCAT('%', :prenom, '%')))")
    Page<Enseignant> searchEnseignants(
            @Param("nom") String nom,
            @Param("prenom") String prenom,
            Pageable pageable);
}
   
 
