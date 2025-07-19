package org.iset.emplois.service;

import java.util.List;
import java.util.Optional;

import org.iset.emplois.model.ECUE;
import org.iset.emplois.model.Matiere;
import org.iset.emplois.model.Parcours;
import org.iset.emplois.model.UE;
import org.iset.emplois.repository.ECUERepository; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ECUEService {
private final ECUERepository  repository;

 
 public Page<ECUE> findEcues(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
     public List<ECUE> findAll() {
        return repository.findAll();
    }
    public Page<ECUE> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<ECUE> findById(Long id) {
        return repository.findById(id);
    }

    public ECUE save(ECUE creneau) {
        return repository.save(creneau);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<ECUE> searchSalles(String nommatfr, String nommatfrabr) {
        return repository.findByNomUEOrDescription(nommatfr, nommatfrabr);
    }


    public Page<ECUE> searchECUEs(String nommatfr, String nommatfrabr, Pageable pageable) {

        System.out.println(nommatfrabr + " fr : "+ nommatfr);
        return repository.searchECUEs(
            nommatfr != null ? nommatfr : "", 
            nommatfrabr != null ? nommatfrabr : "", 
                pageable);
    }

    /**
     * Trouve les matières liées à une UE donnée par son ID.
     *
     * @param ueId l'ID de l'UE
     * @return la liste des matières associées à cette UE
     */
    public List<Matiere> findMatieresByUEId(Long ueId) {
        return repository.findMatieresByUEId(ueId);
    }

    /**
     * Trouve les UEs liées à un parcours donné par son ID.
     *
     * @param parcoursId l'ID du parcours
     * @return la liste des UEs associées à ce parcours
     */
    public List<UE> findUEsByParcoursId(Long parcoursId) {
        return repository.findUEsByParcoursId(parcoursId);
    }

    /**
     * Trouve les parcours liés à un plan d'étude donné par son ID.
     *
     * @param planEtudeId l'ID du plan d'étude
     * @return la liste des parcours associés à ce plan d'étude
     */
    public List<Parcours> findParcoursByPlanEtudeId(Long planEtudeId) {
        return repository.findParcoursByPlanEtudeId(planEtudeId);
    }


     public List<Integer> getNiveauxByPlanEtudeId(Long planId) {
        return repository.findDistinctNiveauxByPlanEtudeId(planId);
    }
 public List<ECUE> findByGroupeAUId(Long groupeauId) {
        return repository.findByGroupeAUId(groupeauId);
    }

}
