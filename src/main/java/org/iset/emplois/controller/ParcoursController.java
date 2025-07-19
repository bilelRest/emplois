package org.iset.emplois.controller;
   
import org.iset.emplois.model.Parcours;
import org.iset.emplois.model.TypeParcours;
import org.iset.emplois.repository.DepartementRepository; 
import org.iset.emplois.service.ParcoursService;
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
@RequestMapping("/parcours")
public class ParcoursController {


    @Autowired
private DepartementRepository departementRepository;
    private final ParcoursService  service;

    public ParcoursController(ParcoursService service) {
        this.service = service;
    }

    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

          
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<Parcours> parcours = service.findParcours(page, size);
        model.addAttribute("parcours", parcours);
        model.addAttribute("currentPageName", "Liste des parcours");
        return "parametres/parcours/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        Parcours parcours   =new Parcours();
        model.addAttribute("currentPageName", "Nouveau parcours");
        model.addAttribute("departementList", departementRepository.findAll());
       
model.addAttribute("parcours", parcours);
    model.addAttribute("types", TypeParcours.values()); // Passer les valeurs de l'énumération
  
        return "parametres/parcours/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Parcours par) {

        System.out.println("Nom parcours ar : "+ par.getNomparcoursar().toString());
        service.save(par);
        return "redirect:/parcours";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération de l'objet
        Parcours   parc= service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid departement ID:" + id));
        model.addAttribute("types", TypeParcours.values()); // Passer les valeurs de l'énumération
        model.addAttribute("departementList", departementRepository.findAll());
        model.addAttribute("currentPageName", "Mise à jour parcours");
        model.addAttribute("parcours", parc);
         return "parametres/parcours/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteEtablissement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "Parcours supprimé avec succès.");
        return "redirect:/parcours";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchParcours(
            @RequestParam(required = false) String nomdepfr,
            @RequestParam(required = false) String nomdepar,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les départements avec pagination
        Page<Parcours> parcoursPage = service.searchParcours(nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("parcours", parcoursPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", parcoursPage.getTotalPages());
        model.addAttribute("nometabfr", nomdepfr);
        model.addAttribute("nometabar", nomdepar);
        model.addAttribute("currentPageName", "Liste des parcours");
        return "parametres/parcours/list";// Nom de la vue Thymeleaf
    }

}
