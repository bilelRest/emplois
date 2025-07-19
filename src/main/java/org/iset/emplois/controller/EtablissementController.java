package org.iset.emplois.controller;
  
import org.iset.emplois.model.Etablissement; 
import org.iset.emplois.service.EtablissementService;
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

@Controller
@RequestMapping("/etablissements")
public class EtablissementController {


    
    private final EtablissementService  service;

    public EtablissementController(EtablissementService service) {
        this.service = service;
    }

    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

          
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<Etablissement> etablissements = service.findEtablissements(page, size);
        model.addAttribute("etablissements", etablissements);
        model.addAttribute("currentPageName", "Liste des établissements");
        return "parametres/etablissement/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        Etablissement   etablissement=new Etablissement();
        
        model.addAttribute("etablissements", service.findAll());
model.addAttribute("etablissement", etablissement);
model.addAttribute("currentPageName", "Nouveau établissement");
        return "parametres/etablissement/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Etablissement etab) {
        service.save(etab);
        return "redirect:/etablissements";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        Etablissement   etablissement= service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid departement ID:" + id));
        
        model.addAttribute("currentPageName", "Mise à jour d'un établissement");
        model.addAttribute("etablissement", etablissement);
         return "parametres/etablissement/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteEtablissement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "etablissement supprimé avec succès.");
        return "redirect:/etablissements";// Redirect back to the list page
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
        Page<Etablissement> etablissementsPage = service.searchEtablissements(nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("etablissements", etablissementsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", etablissementsPage.getTotalPages());
        model.addAttribute("nometabfr", nomdepfr);
        model.addAttribute("nometabar", nomdepar);
        model.addAttribute("currentPageName", "Liste des établissements");
        return "parametres/etablissement/list";// Nom de la vue Thymeleaf
    }

}
