package org.iset.emplois.controller;
 
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.iset.emplois.model.ModuleCours; 
import org.iset.emplois.service.ModuleCoursService;
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
@RequestMapping("/modulecours")
public class ModuleCoursController {


    
    private final ModuleCoursService service;

    public ModuleCoursController(ModuleCoursService service) {
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
                getClass().getResourceAsStream("/reports/modulecours_report.jrxml")
            );

            // Obtenir la liste des départements triés
            List<?> modulecoursList = service.findAll();

            // Créer une source de données JasperReports
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(modulecoursList);

            // Ajouter des paramètres pour le rapport
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("title", "Liste des modules");

            // Remplir le rapport
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Configurer la réponse HTTP
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=departement_report.pdf");

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
            Page<ModuleCours> modulecoursList= service.findModuleCours(page, size);
        model.addAttribute("modulecoursList", modulecoursList);
        model.addAttribute("currentPageName", "Liste des modules");
        return "parametres/modulecours/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        ModuleCours   moduleCours=new ModuleCours();
         
model.addAttribute("moduleCours", moduleCours);
model.addAttribute("currentPageName", "Nouveau département");
        return "parametres/modulecours/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ModuleCours moduleCours) {
        service.save(moduleCours);
        return "redirect:/modulecours";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        ModuleCours moduleCours = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid moduleCours ID:" + id));
        
        System.out.println(  "info dep :"+ moduleCours.toString());
        model.addAttribute("moduleCours", moduleCours);
        model.addAttribute("currentPageName", "Mise à jour Module");
         return "parametres/modulecours/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteDepartement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "Département supprimé avec succès.");
        return "redirect:/modulecours";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchDepartements(
            @RequestParam(required = false) String nomdepfr,
            @RequestParam(required = false) String nomdepar,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les départements avec pagination
        Page<ModuleCours> moduleCoursPage = service.searchModuleCours(nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("moduleCoursPage", moduleCoursPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moduleCoursPage.getTotalPages());
        model.addAttribute("nomdepfr", nomdepfr);
        model.addAttribute("nomdepar", nomdepar);
        model.addAttribute("currentPageName", "Liste des modules");
        return "parametres/modulecours/list";// Nom de la vue Thymeleaf
    }

}
