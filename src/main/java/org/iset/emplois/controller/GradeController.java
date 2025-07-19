package org.iset.emplois.controller;
   
import org.iset.emplois.model.Grade; 
import org.iset.emplois.service.GradeService;
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
@RequestMapping("/grades")
public class GradeController {


    
    private final GradeService  service;

    public GradeController(GradeService service) {
        this.service = service;
    }

    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

          
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<Grade> grades = service.findGrades(page, size);
        model.addAttribute("grades", grades);
        model.addAttribute("currentPageName", "Liste des grades");
        return "parametres/grade/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        Grade   grade=new Grade();
        model.addAttribute("currentPageName", "Nouveau grade");
      
model.addAttribute("grade", grade);
  
        return "parametres/grade/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Grade grade,  RedirectAttributes redirectAttributes) {
        service.save(grade);
         // Ajout d'un message flash pour la redirection
    redirectAttributes.addFlashAttribute("message", "Grade ajouté avec succès.");
    redirectAttributes.addFlashAttribute("alertType", "info"); // Ajout du type d'alerte
      
        return "redirect:/grades";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model)   {
        // Exemple de récupération du créneau
        Grade   grade= service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid departement ID:" + id));
        model.addAttribute("currentPageName", "Mise à jour grade");
        model.addAttribute("grade", grade);
         return "parametres/grade/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteGrade(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "Grade supprimé avec succès.");
        //warning// Ajout du type d'alerte
        redirectAttributes.addFlashAttribute("alertType", "danger"); 
        return "redirect:/grades";// Redirect back to the list page
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
        Page<Grade> gradesPage = service.searchGrades(nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("grades", gradesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", gradesPage.getTotalPages());
        model.addAttribute("nometabfr", nomdepfr);
        model.addAttribute("nometabar", nomdepar);
        model.addAttribute("currentPageName", "Liste des grades");
        return "parametres/grade/list";// Nom de la vue Thymeleaf
    }

}
