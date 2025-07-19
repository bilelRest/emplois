package org.iset.emplois.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
 
import org.iset.emplois.model.Salle;
import org.iset.emplois.repository.SalleRepository;
import org.iset.emplois.security.entities.AppUser;
import org.iset.emplois.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seances")
public class SeanceRestController {
  @Autowired
     SalleRepository salleRepository ;
    

    @Autowired
     SeanceService seanceService;

    @GetMapping
    public List<Map<String, Object>> getAllSeances() {
        return  seanceService.getAllSeances().stream().map(seance -> {
            Map<String, Object> event = new HashMap<>();
            event.put("id", seance.getId());
            event.put("title",seance.getNomEnseignant()+"/"+ seance.getNomMatiere() + " - " + seance.getNomSalle());
            event.put("start", seance.getHeureDebut().toString());
            event.put("end", seance.getHeureFin().toString());
            event.put("dow", getDayOfWeek(seance.getJourSemaine()));
            event.put("className", seance.getNomGroupe());
            return event;
        }).collect(Collectors.toList());
    }
 
    private int getDayOfWeek(String jourSemaine) {
        // Associer les jours de la semaine à un entier pour FullCalendar
        return switch (jourSemaine) {
            case "Lundi" -> 1;
            case "Mardi" -> 2;
            case "Mercredi" -> 3;
            case "Jeudi" -> 4;
            case "Vendredi" -> 5;
            case "Samedi" -> 6;
            default -> 0;
        };
    }


    @GetMapping("/salles/search")
 
public List<Map<String, Object>> searchSalles(@RequestParam(required = false) String term,
                                              Authentication authentication) {
AppUser user = (AppUser) authentication.getPrincipal();
List<Salle> salles = salleRepository.findByDepartementsIn(user.getDepartements());

List<Map<String, Object>> result = salles.stream()
    .map(s -> {
        Map<String, Object> map = new HashMap<>();
        map.put("id", s.getId());
        map.put("nom", s.getNomsalle());
        return map;
    })
    .collect(Collectors.toList());

return result;

}

}
