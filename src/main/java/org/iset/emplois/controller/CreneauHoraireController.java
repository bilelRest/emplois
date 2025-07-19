package org.iset.emplois.controller;

 

import java.util.List;

import org.iset.emplois.model.CreneauHoraire;
import org.iset.emplois.service.CreneauHoraireService;
import org.springframework.data.domain.Page; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
  

@Controller
@RequestMapping("/creneaux")
public class CreneauHoraireController {

    
    private final CreneauHoraireService service;

    public CreneauHoraireController(CreneauHoraireService service) {
        this.service = service;
    }

    @GetMapping
    public String listCreneaux(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Model model) { 

          
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<CreneauHoraire> creneaux = service.findCreneauxHoraires(page, size);
        model.addAttribute("creneaux", creneaux);
        model.addAttribute("currentPageName", "Liste des créneaux horaires");
        return "creneaux/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        CreneauHoraire   creneau=new CreneauHoraire();
        creneau.setCompose(false); // Définir une valeur par défaut
        
        model.addAttribute("creneauxExistants", service.findAll());

        model.addAttribute("currentPageName", "Nouveau créneau horaire");
model.addAttribute("creneau", creneau);
 
        model.addAttribute("creneauxExistants", service.findAll());
        return "creneaux/form";
    }

    @PostMapping("/save")
    public String saveCreneau(@ModelAttribute CreneauHoraire creneau) {
        service.save(creneau);
        return "redirect:/creneaux";
    }

    @GetMapping("/edit/{id}")
    public String editCreneau(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        CreneauHoraire creneau = service.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid Creneau ID: " + id));

    model.addAttribute("creneau", creneau);
    List<CreneauHoraire> creneauxExistants = service.findByComposeFalse();
    model.addAttribute("creneauxExistants", creneauxExistants != null ? creneauxExistants : List.of());
    model.addAttribute("currentPageName", "Mise à jour créneau horaire");
        return "creneaux/form"; // Le nom de la vue Thymeleaf
    }

   

    @GetMapping("/delete/{id}")
    public String deleteCreneau(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/creneaux";
    }
}

