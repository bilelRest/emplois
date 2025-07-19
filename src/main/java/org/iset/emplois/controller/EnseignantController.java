package org.iset.emplois.controller;
   
import org.iset.emplois.model.Enseignant;
import org.iset.emplois.repository.GradeRepository;
import org.iset.emplois.repository.StatutEnseignantRepository;
import org.iset.emplois.service.EnseignantService;
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

@Controller
@RequestMapping("/enseignants")
public class EnseignantController {
    @Autowired
private StatutEnseignantRepository statutEnseignantRepository;
@Autowired
private GradeRepository gradeRepository;
    
    private final EnseignantService service;

    public EnseignantController(EnseignantService service) {
        this.service = service;
    }

    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

          
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<Enseignant> enseignants = service.findenseignants(page, size);
        model.addAttribute("enseignants", enseignants);
        model.addAttribute("currentPageName", "Liste des enseignants");
        return "parametres/enseignant/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        Enseignant   enseignant=new Enseignant();
        
        model.addAttribute("currentPageName", "Nouveau enseignant");

model.addAttribute("statutsList", statutEnseignantRepository.findAll());
        model.addAttribute("gradesList", gradeRepository.findAll());
model.addAttribute("enseignant", enseignant);
  
        return "parametres/enseignant/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Enseignant mat) {
        service.save(mat);
        return "redirect:/enseignants";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        Enseignant enseignant = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid departement ID:" + id));
        
        model.addAttribute("statutsList", statutEnseignantRepository.findAll());
        model.addAttribute("gradesList", gradeRepository.findAll());
        model.addAttribute("enseignant", enseignant);
        model.addAttribute("currentPageName", "Mise à jour enseignant");
         return "parametres/enseignant/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteSalle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the enseignant by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "enseignant supprimé avec succès.");
        return "redirect:/enseignants";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchEnseignants(
            @RequestParam(required = false) String nommatfr,
            @RequestParam(required = false) String nommatfrabr,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les salles avec pagination
        Page<Enseignant> enseignantsPage = service.searchEnseignants(nommatfr, nommatfrabr, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("enseignants", enseignantsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", enseignantsPage.getTotalPages());
        model.addAttribute("nomenseignant", nommatfr);
        model.addAttribute("prenomenseignant", nommatfrabr);
        model.addAttribute("currentPageName", "Liste des enseignants");
        return "parametres/enseignant/list";// Nom de la vue Thymeleaf
    }

}
