package org.iset.emplois.controller;
 
import java.io.OutputStream;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.iset.emplois.model.Departement;
import org.iset.emplois.model.DetailsGroupeAU;
import org.iset.emplois.model.GroupeAU;
import org.iset.emplois.model.JourSemaine;
import org.iset.emplois.model.Salle;
import org.iset.emplois.repository.CreneauHoraireRepository;
import org.iset.emplois.repository.DepartementRepository;
import org.iset.emplois.repository.ECUERepository;
import org.iset.emplois.repository.EnseignantRepository;
import org.iset.emplois.repository.GroupeAURepository;
import org.iset.emplois.repository.ModuleCoursRepository;
import org.iset.emplois.repository.SalleRepository;
import org.iset.emplois.security.entities.AppUser;
import org.iset.emplois.security.repositories.AppUserRepository;
import org.iset.emplois.service.DetailsGroupeAUService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/detailsgroupeaus")
public class DetailsGroupeAUController {



    @Autowired
    private AppUserRepository appUserRepository;


 @Autowired
private DepartementRepository departementRepository;

 @Autowired
private EnseignantRepository enseignantRepository;

 @Autowired
private SalleRepository salleRepository ;

@Autowired
private CreneauHoraireRepository creneauHoraireRepository;

@Autowired
private ECUERepository ecueRepository;

@Autowired
private GroupeAURepository groupeAURepository;

@Autowired
private ModuleCoursRepository moduleCoursRepository;






 



    private final DetailsGroupeAUService service;

    public DetailsGroupeAUController(DetailsGroupeAUService service) {
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
                getClass().getResourceAsStream("/reports/DetailsGroupeAU_report.jrxml")
            );

            // Obtenir la liste des départements triés
            List<?> DetailsAUs = service.findAll();

            // Créer une source de données JasperReports
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(DetailsAUs);

            // Ajouter des paramètres pour le rapport
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("title", "Liste des séances de cours");

            // Remplir le rapport
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Configurer la réponse HTTP
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=DetailsGroupeAU_report.pdf");

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
            Page<DetailsGroupeAU> DetailsGroupeAUs = service.findDetailsGroupeAUs(page, size);
        model.addAttribute("detailsgroupeauList", DetailsGroupeAUs);
        model.addAttribute("currentPageName", "Liste des groupes");
        return "parametres/detailsgroupeau/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        DetailsGroupeAU   detailsgroupeau=new DetailsGroupeAU();




Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  //      AppUser userDetails = (AppUser) auth.getPrincipal();
          
String username = auth.getName(); // login de l'utilisateur
System.out.println("create " +username);
List<Departement> departements =null;
List<Salle> salles =null;
//AppUser userDetails = appUserRepository.findByUsername(username); // Assure-toi que ce repo existe
Optional<AppUser> userOptional = appUserRepository.findByUsername(username);

int semestre=0; //1;
int annee =2025;// LocalDate.now().getYear();
if (userOptional.isPresent()) {
    AppUser user = userOptional.get();
    // utiliser user
    departements= user.getDepartements();
semestre= user.getSemestre();
annee= user.getAnnee();
    System.out.println("departements trouvée : "+ departements.toString());
    salles=salleRepository.findByDepartementsIn(user.getDepartements());
} else {
    // gérer le cas où l'utilisateur n'existe pas (ex : throw exception, retourner null, etc.)
}
      
       model.addAttribute("jourSemaine", JourSemaine.values()); // Passer les valeurs de l'énumération
    model.addAttribute("moduleCoursList", moduleCoursRepository.findAll()); // Passer les valeurs de l'énumération
          
detailsgroupeau.setAnnee(annee);
detailsgroupeau.setSemestre(semestre);
model.addAttribute("detailsgroupeau", detailsgroupeau);

model.addAttribute("departementList",departements);

Long idDepartement = Optional.ofNullable(departements)
    .flatMap(list -> list.stream().findFirst())
    .map(Departement::getId)
    .orElse(-1L);
System.out.println("annne "+ annee + " semestre: "+ semestre + " iddepartement :"+idDepartement);

List<GroupeAU> groupeaus =groupeAURepository.findByAnneeAndSemestre(annee, semestre, idDepartement);
System.out.println("groupeaus "+ groupeaus);
model.addAttribute("groupeauList",groupeaus );
         model.addAttribute("enseignantList", enseignantRepository.findAll());
        model.addAttribute("salleList", salles);
         model.addAttribute("creneauHoraireList", creneauHoraireRepository.findAll());  
  model.addAttribute("ecueList", ecueRepository.findAll());

model.addAttribute("currentPageName", "Création d'une séance de cours");
        return "parametres/detailsgroupeau/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute DetailsGroupeAU dg) {

        System.out.println(dg.toString());
        service.save(dg);
        return "redirect:/detailsaus";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        // Exemple de récupération du créneau
        DetailsGroupeAU detailsgroupeau = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid DetailsAU ID:" + id));
        
        System.out.println(  "info dep :"+ detailsgroupeau.toString());

       

        model.addAttribute("detailsgroupeau", detailsgroupeau); 
       model.addAttribute("jourSemaine", JourSemaine.values()); // Passer les valeurs de l'énumération
    model.addAttribute("moduleCoursList", moduleCoursRepository.findAll()); // Passer les valeurs de l'énumération
          

model.addAttribute("detailsgroupeau", detailsgroupeau);

model.addAttribute("departementList", departementRepository.findAll());
model.addAttribute("groupeauList", groupeAURepository.findAll());
         model.addAttribute("enseignantList", enseignantRepository.findAll());
        model.addAttribute("salleList", salleRepository.findAll());
         model.addAttribute("creneauHoraireList", creneauHoraireRepository.findAll());  
  model.addAttribute("ecueList", ecueRepository.findAll());
  
        model.addAttribute("currentPageName", "Mise à jour séance de cours");
         return "parametres/detailsgroupeau/form"; // Le nom de la vue Thymeleaf
    }

   @PostMapping("/delete/{id}")
    public String deleteDetailsAU(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Your service call to delete the department by ID

        System.out.println("----- Suppression ----"  );
        service.deleteById(id);
        System.out.println("id de suppression  :"+ id );
        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "Séance de cours supprimé avec succès.");
        return "redirect:/detailsgroupeau";// Redirect back to the list page
    }



     @GetMapping("/search")
    public String searchDetailsAUs(
            @RequestParam(required = false) String nomdepfr,
            @RequestParam(required = false) String nomdepar,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les départements avec pagination
        Page<DetailsGroupeAU> detailsgroupeauList = service.searchDetailsGroupeAUs(nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("detailsgroupeauList", detailsgroupeauList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", detailsgroupeauList.getTotalPages());
        model.addAttribute("nomdepfr", nomdepfr);
        model.addAttribute("nomdepar", nomdepar);
        model.addAttribute("currentPageName", "Liste des séances de cours");
        return "parametres/detailsgroupeau/list";// Nom de la vue Thymeleaf
    }

}
