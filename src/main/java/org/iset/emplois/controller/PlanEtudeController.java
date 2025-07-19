package org.iset.emplois.controller;
    
import org.iset.emplois.model.PlanEtude; 
import org.iset.emplois.repository.ParcoursRepository;
import org.iset.emplois.service.PlanEtudeService;
import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/planetudes")
public class PlanEtudeController {


    @Autowired
private ParcoursRepository parcoursRepository;
    
    private final PlanEtudeService  service;

    public PlanEtudeController(PlanEtudeService service) {
        this.service = service;
    }

    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<PlanEtude> planetudes = service.findPlanEtudes(page, size);
        model.addAttribute("planetudes", planetudes);
        model.addAttribute("currentPageName", "Liste des plans d'études");
        return "des/planetude/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        PlanEtude planEtude  =new PlanEtude();

        model.addAttribute("parcoursList", parcoursRepository.findAll());
       
        model.addAttribute("currentPageName", "Nouveau plan d'étude");
      
model.addAttribute("planetude", planEtude);
  
        return "des/planetude/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute PlanEtude planEtude,  RedirectAttributes redirectAttributes) {
 
System.out.println("Plan Etude (Save Method ):"+ planEtude.getId());
        service.save(planEtude);
         // Ajout d'un message flash pour la redirection
    redirectAttributes.addFlashAttribute("message", "Plan étude ajouté avec succès.");
    redirectAttributes.addFlashAttribute("alertType", "info"); // Ajout du type d'alerte
      
        return "redirect:/planetudes";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model)   {
        // Exemple de récupération du créneau
        PlanEtude   planetude= service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Plan Etude ID:" + id));
        model.addAttribute("currentPageName", "Mise à jour plan étude");
        System.out.println("Plan etude en modification " + planetude.toString());
        model.addAttribute("parcoursList", parcoursRepository.findAll());
        model.addAttribute("planetude", planetude);
         return "des/planetude/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteGrade(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "Plan d'étude supprimé avec succès.");
        //warning// Ajout du type d'alerte
        redirectAttributes.addFlashAttribute("alertType", "danger"); 
        return "redirect:/planetudes";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchEtablissements(
            @RequestParam(required = false) String nomdepfr,
            @RequestParam(required = false) String nomdepar,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les départements avec pagination
        Page<PlanEtude> planetudesPage = service.searchPlanEtudes(nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("planetudes", planetudesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", planetudesPage.getTotalPages());
        model.addAttribute("nometabfr", nomdepfr);
        model.addAttribute("nometabar", nomdepar);
        model.addAttribute("currentPageName", "Liste des plans d'études");
        return "des/planetude/list";// Nom de la vue Thymeleaf
    }

}
