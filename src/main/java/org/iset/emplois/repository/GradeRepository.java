package org.iset.emplois.repository;
 
import java.util.List;

import org.iset.emplois.model.Grade; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GradeRepository extends JpaRepository<Grade, Long> {

       Page<Grade> findAll(Pageable pageable);

 
    @Query("SELECT m FROM Grade m WHERE " +
           "(:nomgradefr IS NULL OR LOWER(m.nomgradefr) LIKE LOWER(CONCAT('%', :nomgradefr, '%'))) AND " +
           "(:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    List<Grade> findByNomfrOrDescription(@Param("nomgradefr") String nomgradefr, @Param("description") String description);


    @Query("SELECT m FROM Grade m WHERE " +
           "(:nomgradefr IS NULL OR LOWER(m.nomgradefr) LIKE LOWER(CONCAT('%', :nomgradefr, '%'))) AND " +
           "(:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    Page<Grade> searchGrades(
            @Param("nomgradefr") String nomgradefr,
            @Param("description") String description,
            Pageable pageable);
}
