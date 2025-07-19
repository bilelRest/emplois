package org.iset.emplois.controller;
 
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.iset.emplois.model.GroupeAU; 
import org.iset.emplois.repository.DetailsAURepository; 
import org.iset.emplois.service.GroupeAUService;
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

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/groupeaus")
public class GroupeAUController {

 
        @Autowired
private DetailsAURepository detailsAURepository       ;
 


    private final GroupeAUService service;

    public GroupeAUController(GroupeAUService service) {
        this.service = service;
    }
 @GetMapping("/report")
    public String viewReportPage() {
        return "report";
    }

    @GetMapping("/print-report")
    public void generateReport(HttpServletResponse response) {
        try {
            // Charger le fichier JRXML
            JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/groupeau_report.jrxml")
            );

            // Obtenir la liste des départements triés
            List<?> GroupeAUs = service.findAll();

            // Créer une source de données JasperReports
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(GroupeAUs);

            // Ajouter des paramètres pour le rapport
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("title", "Liste des Départements");

            // Remplir le rapport
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Configurer la réponse HTTP
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=GroupeAU_report.pdf");

            // Exporter le rapport en PDF
            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

          
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<GroupeAU> GroupeAUs = service.findGroupeAUs(page, size);
        model.addAttribute("groupeaus", GroupeAUs);
        model.addAttribute("currentPageName", "Liste des groupes");
        return "parametres/groupeau/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        GroupeAU groupeAU   =new GroupeAU();
           model.addAttribute("detailsauList", detailsAURepository.findAll());
       
      

model.addAttribute("groupeau", groupeAU);
model.addAttribute("currentPageName", "Nouveau Détails AU");
        return "parametres/groupeau/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute GroupeAU dep) {
        service.save(dep);
        return "redirect:/groupeaus";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        GroupeAU groupeAU = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid GroupeAU ID:" + id));
        
       // System.out.println(  "info dep :"+ groupeAU.toString());
        model.addAttribute("groupeau", groupeAU);
           model.addAttribute("detailsauList", detailsAURepository.findAll());
      
   
        model.addAttribute("currentPageName", "Mise à jour Groupe");
         return "parametres/groupeau/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteGroupeAU(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "Détails Groupe supprimé avec succès.");
        return "redirect:/groupeaus";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchGroupeAUs(
            @RequestParam(required = false) String nomdepfr,
            @RequestParam(required = false) String nomdepar,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les départements avec pagination
        Page<GroupeAU> GroupeAUsPage = service.searchGroupeAUs(nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("groupeaus", GroupeAUsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", GroupeAUsPage.getTotalPages());
        model.addAttribute("nomdepfr", nomdepfr);
        model.addAttribute("nomdepar", nomdepar);
        model.addAttribute("currentPageName", "Liste des groupes");
        return "parametres/groupeau/list";// Nom de la vue Thymeleaf
    }

}
