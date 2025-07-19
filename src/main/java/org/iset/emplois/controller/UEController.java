package org.iset.emplois.controller;
    
import java.util.List;

import org.iset.emplois.model.Parcours;
import org.iset.emplois.model.TypeUE;
import org.iset.emplois.model.UE;
import org.iset.emplois.service.ParcoursService;
import org.iset.emplois.service.UEService; 
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
@RequestMapping("/ues")
@AllArgsConstructor
public class UEController {

    private final UEService  service;

private final ParcoursService parcoursService;

     

    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

          
        model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<UE> ues = service.findUEs(page, size);
        model.addAttribute("ues", ues);
        model.addAttribute("currentPageName", "Liste des UEs");
        return "des/ue/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        UE ue   =new UE();
        model.addAttribute("currentPageName", "Nouveau Ue");
model.addAttribute("ue", ue);
  List<Parcours> parcoursExistants=parcoursService.findAll();
        model.addAttribute("parcoursExistants", parcoursExistants);


    model.addAttribute("types", TypeUE.values()); // Passer les valeurs de l'énumération
  
        return "des/ue/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute UE ue) {

 
        service.save(ue);
        return "redirect:/ues";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération de l'objet
        UE   ue= service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid UE ID:" + id));
        model.addAttribute("types", TypeUE.values()); // Passer les valeurs de l'énumération
        model.addAttribute("currentPageName", "Mise à jour");
        model.addAttribute("ue", ue);
          List<Parcours> parcoursExistants=parcoursService.findAll();
        model.addAttribute("parcoursExistants", parcoursExistants);

         return "des/ue/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteEtablissement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID
        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "UE supprimé avec succès.");
        return "redirect:/ues";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchUEs(
            @RequestParam(required = false) String nomdepfr,
            @RequestParam(required = false) String nomdepar,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les départements avec pagination
        Page<UE> uePage = service.searchUEs (nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("ues", uePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", uePage.getTotalPages());
        model.addAttribute("nom", nomdepfr);
        model.addAttribute("description", nomdepar);
        model.addAttribute("currentPageName", "Liste des UEs");
        return "des/ue/list";// Nom de la vue Thymeleaf
    }

}
