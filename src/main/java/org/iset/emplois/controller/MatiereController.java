package org.iset.emplois.controller;
  
import java.util.List; 
import org.iset.emplois.model.Departement;
import org.iset.emplois.model.Matiere;
import org.iset.emplois.service.DepartementService;
import org.iset.emplois.service.MatiereService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/matieres")
public class MatiereController {


    
    private final MatiereService service;
    
private final DepartementService departementService;

    public MatiereController(MatiereService service,DepartementService departementService) {
        this.service = service;
        this.departementService=departementService;
    }

    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

            model.addAttribute("currentPageName", "Liste des matières");
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<Matiere> matieres = service.findmatieres(page, size);
        model.addAttribute("matieres", matieres);
        return "parametres/matiere/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        Matiere   matiere=new Matiere();
        model.addAttribute("currentPageName", "Nouvelle matière");
        model.addAttribute("matieres", service.findAll());
model.addAttribute("matiere", matiere);
     List<Departement> depExistants=departementService.findAll();
    model.addAttribute("depExistants", depExistants);
        return "parametres/matiere/form";
    }

    @PostMapping("/save")
    public String saveMatiere(@Valid @ModelAttribute("matiere") Matiere matiere,
                              BindingResult bindingResult, Model model) {

   if (service.existsByNommatfr(matiere.getNommatfr(), matiere.getId())) {
     List<Departement> depExistants=departementService.findAll();
    model.addAttribute("depExistants", depExistants);
        bindingResult.rejectValue("nommatfr", "error.nommatfr", "Le nom de la matière (FR) existe déjà !");
    }

    if (service.existsByNommatfrabr(matiere.getNommatfrabr(), matiere.getId())) {
         List<Departement> depExistants=departementService.findAll();
    model.addAttribute("depExistants", depExistants);
        bindingResult.rejectValue("nommatfrabr", "error.nommatfrabr", "L'abréviation existe déjà !");
    }

    if (bindingResult.hasErrors()) {
        return "parametres/matiere/form";
    }

    service.save(matiere);

    return "redirect:/matieres";

         
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        Matiere matiere = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid departement ID:" + id));
        
        model.addAttribute("currentPageName", "Mise à jour d'une matière");
        model.addAttribute("matiere", matiere);
           List<Departement> depExistants=departementService.findAll();
    model.addAttribute("depExistants", depExistants);
         return "parametres/matiere/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteDepartement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the matiere by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "matiere supprimé avec succès.");
        return "redirect:/matieres";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchMatieres(
            @RequestParam(required = false) String nommatfr,
            @RequestParam(required = false) String nommatfrabr,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les matieres avec pagination
        Page<Matiere> matieresPage = service.searchMatieres(nommatfr, nommatfrabr, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("matieres", matieresPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", matieresPage.getTotalPages());
        model.addAttribute("nommatfr", nommatfr);
        model.addAttribute("nommatfrabr", nommatfrabr);
        model.addAttribute("currentPageName", "Liste des matières");
        return "parametres/matiere/list";// Nom de la vue Thymeleaf
    }

}
