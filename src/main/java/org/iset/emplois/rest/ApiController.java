package org.iset.emplois.rest;

import java.util.List;

import org.iset.emplois.dto.ECUEDto;
import org.iset.emplois.dto.GroupeAUDto;
import org.iset.emplois.model.ECUE;
import org.iset.emplois.model.GroupeAU;
import org.iset.emplois.model.Matiere;
import org.iset.emplois.model.Parcours;
import org.iset.emplois.model.PlanEtude;
import org.iset.emplois.model.UE;
import org.iset.emplois.repository.PlanEtudeRepository;
import org.iset.emplois.service.ECUEService;
import org.iset.emplois.service.GroupeAUService;
import org.iset.emplois.service.ParcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
@Autowired
    private ECUEService ecueService; // Service pour gérer les données

@Autowired
    private ParcoursService parcoursService; // Service pour gérer les données

@Autowired
    private GroupeAUService  groupeAUService; // Service pour gérer les données


    @Autowired
    private PlanEtudeRepository planEtudeRepository;

    @GetMapping("/parcours/{planEtudeId}")
    public List<Parcours> getParcoursByPlanEtude(@PathVariable Long planEtudeId) {
           System.out.println("Choix Plan Etude (id) :"+planEtudeId);
        return parcoursService.findParcoursByPlanEtudeId(planEtudeId);
    }

    @GetMapping("/ues/{parcoursId}")
    public List<UE> getUesByParcours(@PathVariable Long parcoursId) {
     
        return ecueService.findUEsByParcoursId(parcoursId);
    }

    @GetMapping("/matieres/{ueId}")
    public List<Matiere> getMatieresByUE(@PathVariable Long ueId) {
        return ecueService.findMatieresByUEId(ueId);

    }
         @GetMapping("/byParcoursAndAnnee")
    public List<PlanEtude> getByParcoursAndAnnee(@RequestParam Long parcoursId, @RequestParam int annee) {
        return planEtudeRepository.findByParcoursIdAndAnnee(parcoursId, annee);
    }
 

    @GetMapping("/niveaux/{planId}")
    public ResponseEntity<List<Integer>> getNiveauxByPlan(@PathVariable Long planId) {
        List<Integer> niveaux = ecueService.getNiveauxByPlanEtudeId(planId);
        return ResponseEntity.ok(niveaux);
    }


@GetMapping("/groupes-au")
public  ResponseEntity<List<GroupeAUDto>> getGroupesAU(
        @RequestParam int annee,
        @RequestParam int semestre,
        @RequestParam Long departementId) {
    List<GroupeAU> groupes = groupeAUService.findByAnneeAndSemestre(annee, semestre, departementId);
    List<GroupeAUDto> dtos = groupes.stream()
            .map(g -> new GroupeAUDto(g.getId(), g.getNomgroupe()))
            .toList();
  return ResponseEntity.ok(dtos);
   
}

@GetMapping("/detailsgroupeau/ecues/{groupeauId}")
public  ResponseEntity<List<ECUEDto>> findByGroupeAUId(
         @PathVariable Long groupeauId) {
    List<ECUE> ecues = ecueService.findByGroupeAUId(groupeauId);

 List<ECUEDto> dtos = ecues.stream()
            .map(e -> new ECUEDto(e.getId(), e.getMatiere().getNommatfr()))
            .toList();
  return ResponseEntity.ok(dtos);
 
   
}


}
