package org.iset.emplois.controller;
 
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iset.emplois.model.AU; 
import org.iset.emplois.service.AUService; 
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
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/aus")
@AllArgsConstructor
public class AUController {


    
    private final AUService service;
// private final  PlanEtudeService planEtudeService;

   
 @GetMapping("/report")
    public String viewReportPage() {
        return "report";
    }

    @GetMapping("/print-report")
    public void generateReport(HttpServletResponse response) {
        try {
            // Charger le fichier JRXML
            JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/au_report.jrxml")
            );

            // Obtenir la liste des départements triés
            List<?> aus = service.findAll();

            // Créer une source de données JasperReports
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(aus);

            // Ajouter des paramètres pour le rapport
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("title", "Liste des AUs");

            // Remplir le rapport
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Configurer la réponse HTTP
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=au_report.pdf");

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
            Page<AU> aus = service.findAUs(page, size);
        model.addAttribute("aus", aus);
        model.addAttribute("currentPageName", "Liste des Années Universitaires");
        return "parametres/au/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        AU   au=new AU();
         
model.addAttribute("au", au);
model.addAttribute("currentPageName", "Nouveau AU");

 // List<PlanEtude> planEtudesExistants=planEtudeService.findAll();
 //       model.addAttribute("planEtudesExistants", planEtudesExistants);

        return "parametres/au/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AU dep) {
        service.save(dep);
        return "redirect:/aus";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        AU au = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid au ID:" + id));
        
        System.out.println(  "info dep :"+ au.toString());
        model.addAttribute("au", au);
        model.addAttribute("currentPageName", "Mise à jour AU");
        
 // List<PlanEtude> planEtudesExistants=planEtudeService.findAll();
 //       model.addAttribute("planEtudesExistants", planEtudesExistants);
         return "parametres/au/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteAU(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "AU supprimé avec succès.");
        return "redirect:/aus";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchAUs(
            @RequestParam(required = false) String nomaufr,
            @RequestParam(required = false) String nomauar,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les départements avec pagination
        Page<AU> ausPage = service.searchAUs(nomaufr, nomauar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("aus", ausPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ausPage.getTotalPages());
        model.addAttribute("nomdepfr", nomaufr);
        model.addAttribute("nomdepar", nomauar);
        model.addAttribute("currentPageName", "Liste des AUs");
        return "parametres/au/list";// Nom de la vue Thymeleaf
    }

}
