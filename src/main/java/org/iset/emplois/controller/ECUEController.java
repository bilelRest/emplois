package org.iset.emplois.controller;
   
import java.util.List;
import org.iset.emplois.model.ECUE;
import org.iset.emplois.model.Matiere;
import org.iset.emplois.model.Parcours;
import org.iset.emplois.model.PlanEtude;
import org.iset.emplois.model.UE; 
import org.iset.emplois.service.ECUEService;
import org.iset.emplois.service.MatiereService;
import org.iset.emplois.service.ParcoursService;
import org.iset.emplois.service.PlanEtudeService;
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
@RequestMapping("/ecues")
@AllArgsConstructor
public class ECUEController {


    
    private final ECUEService service; 
private final PlanEtudeService planEtudeService;
private final MatiereService matiereService;
private final UEService ueService;
private final ParcoursService parcoursService;

 
    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

            model.addAttribute("currentPageName", "Liste des ECUEs");
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<ECUE> ecues = service.findEcues(page, size);
        model.addAttribute("ecues", ecues);
        return "des/ecue/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        ECUE   ecue=new ECUE();
        model.addAttribute("currentPageName", "Nouvelle ECUE");
        List<PlanEtude> planEtudesExistants=planEtudeService.findAll();
        model.addAttribute("planEtudesExistants", planEtudesExistants);

        List<Matiere> matieresExistants=matiereService.findAll();
        model.addAttribute("matieresExistants", matieresExistants);

        List<Parcours> parcoursExistants=parcoursService.findAll();
        model.addAttribute("parcoursExistants", parcoursExistants);


        List<UE> ueExistants=ueService.findAll();
        model.addAttribute("ueExistants", ueExistants);


model.addAttribute("ecue", ecue); 
        return "des/ecue/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ECUE ecue, RedirectAttributes redirectAttributes) {
        service.save(ecue);

             // Optionally add a success message
             redirectAttributes.addFlashAttribute("message", "ECUE mise à jour avec succès.");
             //warning// Ajout du type d'alerte
             redirectAttributes.addFlashAttribute("alertType", "success"); 
        return "redirect:/ecues";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        ECUE ecue = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid departement ID:" + id));
        List<PlanEtude> planEtudesExistants=planEtudeService.findAll();
        model.addAttribute("planEtudesExistants", planEtudesExistants);

        List<Matiere> matieresExistants=matiereService.findAll();
        model.addAttribute("matieresExistants", matieresExistants);

        List<Parcours> parcoursExistants=parcoursService.findAll();
        model.addAttribute("parcoursExistants", parcoursExistants);


        List<UE> ueExistants=ueService.findAll();
        model.addAttribute("ueExistants", ueExistants);

        model.addAttribute("currentPageName", "Mise à jour d'une ECUE");
        model.addAttribute("ecue", ecue);
         return "des/ecue/form"; // Le nom de la vue Thymeleaf
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
        return "redirect:/ecues";// Redirect back to the list page
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
        Page<ECUE> ecuesPage = service.searchECUEs(nommatfr, nommatfrabr, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("ecues", ecuesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ecuesPage.getTotalPages());
        model.addAttribute("nomsalle", nommatfr);
        model.addAttribute("description", nommatfrabr);

        return "des/ecue/list";// Nom de la vue Thymeleaf
    }

}
