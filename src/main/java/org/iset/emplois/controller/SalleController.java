package org.iset.emplois.controller;
   
import java.util.List;

import org.iset.emplois.model.Departement;
import org.iset.emplois.model.Salle;
import org.iset.emplois.service.DepartementService;
import org.iset.emplois.service.SalleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/salles")
@AllArgsConstructor
public class SalleController {


    
    private final SalleService service;
private final DepartementService departementService;
   

    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

            model.addAttribute("currentPageName", "Liste des salles");
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<Salle> salles = service.findsalles(page, size);
        model.addAttribute("salles", salles);
        return "parametres/salle/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        Salle   salle=new Salle();
        model.addAttribute("currentPageName", "Nouvelle salle");
        model.addAttribute("salles", service.findAll());
model.addAttribute("salle", salle);
      List<Departement> depExistants=departementService.findAll();
    model.addAttribute("depExistants", depExistants);
        return "parametres/salle/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Salle mat, RedirectAttributes redirectAttributes) {
        service.save(mat);

             // Optionally add a success message
             redirectAttributes.addFlashAttribute("message", "Salle mise à jour avec succès.");
             //warning// Ajout du type d'alerte
             redirectAttributes.addFlashAttribute("alertType", "success"); 
        return "redirect:/salles";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        Salle salle = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid departement ID:" + id));
        List<Departement> depExistants=departementService.findAll();
        model.addAttribute("depExistants", depExistants);
        model.addAttribute("currentPageName", "Mise à jour d'une salle");
        model.addAttribute("salle", salle);
         return "parametres/salle/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteSalle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the matiere by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
          // Optionally add a success message
          redirectAttributes.addFlashAttribute("message", "Salle supprimé avec succès.");
          //warning// Ajout du type d'alerte
          redirectAttributes.addFlashAttribute("alertType", "danger"); 
        return "redirect:/salles";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchSalles(
            @RequestParam(required = false) String nommatfr,
            @RequestParam(required = false) String nommatfrabr,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les salles avec pagination
        Page<Salle> sallesPage = service.searchSalles(nommatfr, nommatfrabr, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("salles", sallesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sallesPage.getTotalPages());
        model.addAttribute("nomsalle", nommatfr);
        model.addAttribute("description", nommatfrabr);

        return "parametres/salle/list";// Nom de la vue Thymeleaf
    }

}
