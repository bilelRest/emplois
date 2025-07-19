package org.iset.emplois.controller;
 

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.iset.emplois.model.Seance;
import org.iset.emplois.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/seances")
public class SeanceController {

       @Autowired
    private SeanceService seanceService;

    @GetMapping
    public String getCalendar(Model model) {
        model.addAttribute("seance", new Seance()); // Nouvelle séance pour le formulaire d'ajout
       
        String[] joursSemaine = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
        model.addAttribute("joursSemaine", joursSemaine);
        model.addAttribute("seances", seanceService.getAllSeances());


        // Créneau horaire de 8h à 18h
        List<Integer> heures = IntStream.rangeClosed(8, 18).boxed().collect(Collectors.toList());
        model.addAttribute("heures", heures);
        return "calendar";
    }

    @GetMapping("/Js")
    public String getCalendarJs( ) {
      
        return "calendarJs";
    }


    @PostMapping("/add")
    public String addSeance(@ModelAttribute Seance seance) {
        System.out.println(seance.toString());
        seanceService.addSeance(seance);
        return "redirect:/seances";
    }

    @PostMapping("/update")
    public String updateSeance(@ModelAttribute Seance seance) {
        seanceService.updateSeance(seance);
        return "redirect:/seances";
    }

    @PostMapping("/delete")
    public String deleteSeance(@RequestParam Long id) {
        seanceService.deleteSeance(id);
        return "redirect:/seances";
    }




    @GetMapping("/calendar2")
    public String showCalendar(Model model) {
        // Récupère toutes les séances et les convertit en Map pour le calendrier
        List<Map<String, Object>> events = seanceService.getAllSeances().stream().map(seance -> {
            Map<String, Object> event = new HashMap<>();
            event.put("id", seance.getId());
            event.put("title", seance.getNomMatiere() + " - " + seance.getNomSalle());
            event.put("start", seance.getHeureDebut().toString());
            event.put("end", seance.getHeureFin().toString());
            event.put("dow", getDayOfWeek(seance.getJourSemaine()));
            event.put("className", seance.getNomGroupe());
            return event;
        }).collect(Collectors.toList());
System.out.println("Calender 2 chargement");
        // Ajouter les événements au modèle
        model.addAttribute("events", events);

        // Retourner le nom de la vue
        return "calendar2";
    }

    // Méthode utilitaire pour obtenir le jour de la semaine
    private int getDayOfWeek(String jourSemaine) {
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
}
