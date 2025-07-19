package org.iset.emplois.security.controllers;
 
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.iset.emplois.security.entities.AppRole; 
import org.iset.emplois.security.entities.Menu;
import org.iset.emplois.security.service.AppRoleService;
import org.iset.emplois.security.service.MenuService;
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
@RequestMapping("/roles")
@AllArgsConstructor
public class AppRoleController {


    
    private final AppRoleService  service;
private final MenuService menuService;
     
    @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

          
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<AppRole> roles = service.findRoles(page, size);
        model.addAttribute("roles", roles);
        model.addAttribute("currentPageName", "Liste des roles");
        return "administration/roles/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        AppRole   role=new AppRole();
         List<Menu> menus = menuService.getAllMenus();
        
       
        model.addAttribute("menusExistants", menus);
         
model.addAttribute("role", role);
model.addAttribute("currentPageName", "Nouveau");
        return "administration/roles/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AppRole role) {
        service.save(role);
        return "redirect:/roles";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        AppRole role = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role ID:" + id));
        List<Menu> menus = menuService.getAllMenus();
        
       
        model.addAttribute("menusExistants", menus);
        model.addAttribute("role", role);
        model.addAttribute("currentPageName", "Mise à jour département");
         return "administration/roles/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteDepartement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "Role supprimé avec succès.");
        return "redirect:/roles";// Redirect back to the list page
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
        Page<AppRole> rolessPage = service.searchRoles(nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("departements", rolessPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", rolessPage.getTotalPages());
        model.addAttribute("nomdepfr", nomdepfr);
        model.addAttribute("nomdepar", nomdepar);
        model.addAttribute("currentPageName", "Liste des roles");
        return "administration/roles/list";// Nom de la vue Thymeleaf
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
             getClass().getResourceAsStream("/reports/role_report.jrxml")
         );

         // Obtenir la liste des départements triés
         List<?> departements = service.findAll();

         // Créer une source de données JasperReports
         JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(departements);

         // Ajouter des paramètres pour le rapport
         Map<String, Object> parameters = new HashMap<>();
         parameters.put("title", "Liste des roles");

         // Remplir le rapport
         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

         // Configurer la réponse HTTP
         response.setContentType("application/pdf");
         response.setHeader("Content-Disposition", "inline; filename=role_report.pdf");

         // Exporter le rapport en PDF
         OutputStream outputStream = response.getOutputStream();
         JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
         outputStream.flush();
         outputStream.close();

     } catch (Exception e) {
         e.printStackTrace();
     }
 }

}
