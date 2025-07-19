package org.iset.emplois.service;

import org.iset.emplois.model.CreneauHoraire;
import org.iset.emplois.repository.CreneauHoraireRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CreneauHoraireService {
    private final CreneauHoraireRepository repository;

    public CreneauHoraireService(CreneauHoraireRepository repository) {
        this.repository = repository;
    }


      public Page<CreneauHoraire> findCreneauxHoraires(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    public List<CreneauHoraire> findByComposeFalse() {
        return repository.findByComposeFalse();
    }
//findByComposeFalse
    public List<CreneauHoraire> findAll() {
        return repository.findAll();
    }
    public Page<CreneauHoraire> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<CreneauHoraire> findById(Long id) {
        return repository.findById(id);
    }

    public CreneauHoraire save(CreneauHoraire creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

