package org.iset.emplois.controller;
   
import org.iset.emplois.model.Groupe; 
import org.iset.emplois.repository.ParcoursRepository;
import org.iset.emplois.service.GroupeService;
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
@RequestMapping("/groupes")
public class GroupeController {

 @Autowired
    private ParcoursRepository parcoursRepository;

    
    private final GroupeService  service;

    public GroupeController(GroupeService service) {
        this.service = service;
    }

    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

          
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<Groupe> groupes = service.findParcours(page, size);
            model.addAttribute("currentPageName", "Liste des groupes");
        model.addAttribute("groupes", groupes);
        return "parametres/groupe/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        Groupe   groupe=new Groupe();
        model.addAttribute("currentPageName", "Nouveau groupe");
model.addAttribute("groupe", groupe);
  model.addAttribute("parcoursList", parcoursRepository.findAll());
        return "parametres/groupe/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Groupe groupe) {
        service.save(groupe);
        return "redirect:/groupes";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération de l'objet
        Groupe   groupe= service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid departement ID:" + id));
        
        model.addAttribute("currentPageName", "Mise à jour d'un groupe");
  model.addAttribute("parcoursList", parcoursRepository.findAll());
        model.addAttribute("groupe", groupe);
         return "parametres/groupe/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteEtablissement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "Parcours supprimé avec succès.");
        return "redirect:/groupes";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchGroupes(
            @RequestParam(required = false) String nomdepfr,
            @RequestParam(required = false) String nomdepar,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les départements avec pagination
        Page<Groupe> groupesPage = service.searchGroupes(nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("groupes", groupesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", groupesPage.getTotalPages());
        model.addAttribute("nometabfr", nomdepfr);
        model.addAttribute("nometabar", nomdepar);
        model.addAttribute("currentPageName", "Liste des groupes");
        return "parametres/groupe/list";// Nom de la vue Thymeleaf
    }

}
