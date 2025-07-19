package org.iset.emplois.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iset.emplois.model.Seance;
import org.iset.emplois.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seances2")
public class SeanceController2 {
 @Autowired
    private SeanceService seanceService;

    @GetMapping("/all")
    public List<Map<String, Object>> getAllSeances() {
        List<Seance> seances = seanceService.getAllSeances();  // Récupère toutes les séances
        List<Map<String, Object>> events = new ArrayList<>();
        
        for (Seance seance : seances) {
            Map<String, Object> event = new HashMap<>();
            event.put("id", seance.getId());
            event.put("title",seance.getNomEnseignant() +"/"+seance.getNomMatiere() + " - " + seance.getNomSalle());
            
            // Utilisation des méthodes pour récupérer les dates de début et de fin
            LocalDateTime startDateTime = seance.getStartDateTime();
            LocalDateTime endDateTime = seance.getEndDateTime();
            
            // Conversion des LocalDateTime en format ISO
            String startTime = startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String endTime = endDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            
            event.put("start", startTime);  // Format ISO pour FullCalendar
            event.put("end", endTime);      // Format ISO pour FullCalendar
            
            event.put("dow", getDayOfWeek(seance.getJourSemaine())); // Conversion du jour de la semaine
            
            event.put("className", seance.getNomGroupe());  // Classe CSS personnalisée pour le style
            
            events.add(event);
        }
        
        return events;  // Retourne la liste des événements
    }

    // Fonction pour mapper le jour de la semaine en format numérique
    private List<Integer> getDayOfWeek(String jourSemaine) {
        switch (jourSemaine) {
            case "Lundi": return Arrays.asList(1);
            case "Mardi": return Arrays.asList(2);
            case "Mercredi": return Arrays.asList(3);
            case "Jeudi": return Arrays.asList(4);
            case "Vendredi": return Arrays.asList(5);
            case "Samedi": return Arrays.asList(6);
            default: return Arrays.asList();
        }
    }
}
