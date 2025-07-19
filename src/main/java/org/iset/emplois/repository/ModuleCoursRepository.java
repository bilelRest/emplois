package org.iset.emplois.repository;
 
import java.util.List;
  
import org.iset.emplois.model.ModuleCours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModuleCoursRepository extends JpaRepository<ModuleCours, Long> {
 Page<ModuleCours> findAll(Pageable pageable);

  // Méthode de recherche par nomdepfr ou nomdepar
    @Query("SELECT m FROM ModuleCours m WHERE " +
           "(:nommodulefr IS NULL OR LOWER(m.nommodulefr) LIKE LOWER(CONCAT('%', :nommodulefr, '%'))) AND " +
           "(:nommodulear IS NULL OR LOWER(m.nommodulear) LIKE LOWER(CONCAT('%', :nommodulear, '%')))")
    List<ModuleCours> findByNomModulefrOrNomModulear(@Param("nommodulefr") String nommodulefr, @Param("nommodulear") String nommodulear);


    @Query("SELECT m FROM ModuleCours m WHERE " +
           "(:nommodulefr IS NULL OR LOWER(m.nommodulefr) LIKE LOWER(CONCAT('%', :nommodulefr, '%'))) AND " +
           "(:nommodulear IS NULL OR LOWER(m.nommodulear) LIKE LOWER(CONCAT('%', :nommodulear, '%')))")
    Page<ModuleCours> searchModuleCours(
            @Param("nommodulefr") String nommodulefr,
            @Param("nommodulear") String nommodulear,
            Pageable pageable);
}
   
 
