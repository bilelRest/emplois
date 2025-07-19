package org.iset.emplois.repository;

import java.util.List;

import org.iset.emplois.model.CreneauHoraire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreneauHoraireRepository extends JpaRepository<CreneauHoraire, Long> {
    Page<CreneauHoraire> findAll(Pageable pageable);
    
    List<CreneauHoraire> findByComposeFalse();
}

