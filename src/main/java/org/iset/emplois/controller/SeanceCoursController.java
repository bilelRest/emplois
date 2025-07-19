package org.iset.emplois.controller;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
 
import org.iset.emplois.model.JourSemaine;
import org.iset.emplois.model.SeanceCours;
import org.iset.emplois.security.entities.Menu;
import org.iset.emplois.security.repositories.MenuRepository;
import org.iset.emplois.service.SeanceCoursService;
import org.iset.emplois.service.SeanceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SeanceCoursController {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private SeanceCoursService seanceCoursService;
@Autowired
    private SeanceManager seanceManager;

    @GetMapping("/saisieseance")
    public String afficher_formSaisieSeanceCours(Model model) {

        
        List<String> enseignants = seanceCoursService.getEnseignantsDistincts();   
        List<String> departements = seanceCoursService.getDepartementsDistincts();

        List<String> groupes = seanceCoursService.getGroupesDistincts();

        List<String> salles = seanceCoursService.getSallesDistincts(); 

        List<String> matieres = seanceCoursService.getMatieresDistincts(); 
        List<String> modules = seanceCoursService.getModulesDistincts(); 

        List<String> jourSemaine = seanceCoursService.getJourSemainesDistincts(); 

 
        List<String> creneauxHoraires = seanceCoursService.getCreneauHorairesDistincts(); 

 


        model.addAttribute("departements", departements);
        model.addAttribute("groupes", groupes);
        model.addAttribute("salles", salles);
        model.addAttribute("enseignants", enseignants); 
        model.addAttribute("matieres", matieres); 
        model.addAttribute("modules", modules); 
        model.addAttribute("jourSemaine", jourSemaine); 
        model.addAttribute("creneauxHoraires", creneauxHoraires); 

    SeanceCours seanceCours= new SeanceCours();
    model.addAttribute("seancecours", seanceCours); 
    model.addAttribute("currentPageName", "Liste des créneaux horaires");
        return "SaisieSeanceCours";
    }


    @GetMapping("/emplois")
    public String getEmploiDuTemps(Model model) {

        System.out.println("Nombre :" + seanceCoursService.getAllSeanceCoursses());
        // Ajouter tous les jours de la semaine
        Map<JourSemaine, List<SeanceCours>> cellulesParJour = new HashMap<>();
        for (JourSemaine jour : JourSemaine.values()) {
            // Utiliser getCellulesByJour() pour les jours ayant des cellules, ou une liste
            // vide
            cellulesParJour.put(jour, seanceCoursService.getCellulesByJour(jour));
        }

        model.addAttribute("joursSemaine", JourSemaine.values());
        model.addAttribute("cellules", cellulesParJour);

        return "emplois";
    }

    @GetMapping("/emplois2")
    public String getEmploiDuTemps2(Model model) {

        System.out.println("Nombre :" + seanceCoursService.getAllSeanceCoursses());
        model.addAttribute("cellules", seanceCoursService.getAllSeanceCoursses());

        model.addAttribute("joursSemaine", JourSemaine.values());

        return "emplois2";
    }

    @GetMapping("/emplois3")
    public String afficherEmploi(Model model,
    @RequestParam(defaultValue = "GROUPE") String typeaffichage,
    @RequestParam(defaultValue = "DSI 31") String nom
    ) {
        // Récupérer les jours de la semaine
        model.addAttribute("joursSemaine", JourSemaine.values());

        // Organiser les cellules par jour de la semaine
        Map<JourSemaine, List<SeanceCours>> emploiDuTemps = new LinkedHashMap<>();

        for (JourSemaine jour : JourSemaine.values()) {
           // List<SeanceCours> cellules = seanceCoursService.getCellulesByJour(jour);

           List<SeanceCours> cellules = new ArrayList<>(); // Déclarer la variable avant le switch

switch (typeaffichage) {
    case "GROUPE":
        cellules = seanceCoursService.getSeancesByJourSemaineAndNomGroupe(jour, nom);
        break;
    case "SALLE":
        cellules = seanceCoursService.getSeancesByJourSemaineAndNomSalle(jour, nom);
        break;
    case "ENSEIGNANT":
        cellules = seanceCoursService.getSeancesByJourSemaineAndNomEnseignant(jour, nom);
        break;
    default:
        System.out.println("Type d'affichage inconnu : " + typeaffichage);
        break;
}

 

            System.out.println("Cellule" + jour.toString() + " :" + cellules.toString());
            emploiDuTemps.put(jour, cellules);
            cellules.clear();
        }

        System.out.println(emploiDuTemps.toString());
        model.addAttribute("emploiDuTemps", emploiDuTemps);
        return "emplois3";
    }
    @GetMapping("/emplois4")
    public String afficherEmploi4(Model model) {
        // Récupérer les jours de la semaine
        model.addAttribute("joursSemaine", JourSemaine.values());

        // Organiser les cellules par jour de la semaine
        Map<JourSemaine, List<SeanceCours>> emploiDuTemps = new LinkedHashMap<>();

        for (JourSemaine jour : JourSemaine.values()) {
            List<SeanceCours> cellules = seanceCoursService.getCellulesByJour(jour);
            System.out.println("Cellule" + jour.toString() + " :" + cellules.toString());
            emploiDuTemps.put(jour, cellules);
        }

        System.out.println(emploiDuTemps.toString());
        model.addAttribute("emploiDuTemps", emploiDuTemps);
        return "emplois4";
    }




    @GetMapping("/emplois5")
    public String afficherEmploi5(
    @RequestParam(defaultValue = "GROUPE") String typeaffichage,
    @RequestParam(defaultValue = "TI") String departement,
    @RequestParam(defaultValue = "DSI 34") String groupe,
    @RequestParam(defaultValue = "") String salle,
    @RequestParam(defaultValue = "N.M") String enseignant,
    Model model)  {
        // Récupérer les jours de la semaine
        model.addAttribute("joursSemaine", JourSemaine.values());

        // Organiser les cellules par jour de la semaine
       // Déclaration du Map de Map
       Map<JourSemaine, Map<Integer, List<SeanceCours>>> emploiDuTemps = new LinkedHashMap<>();
String nom="Bonjour";

        for (JourSemaine jour : JourSemaine.values()) {
            seanceManager.init();
          //  List<SeanceCours> cellules = seanceCoursService.getCellulesByJour(jour);
          List<SeanceCours> cellules = new ArrayList<>(); // Déclarer la variable avant le switch

          switch (typeaffichage) {
              case "GROUPE":
              nom =  "GROUPE :"+ groupe;
                  cellules = seanceCoursService.getSeancesByJourSemaineAndNomGroupe(jour, groupe);
                  break;
              case "SALLE":
              nom =  "SALLE :"+ salle;
                  cellules = seanceCoursService.getSeancesByJourSemaineAndNomSalle(jour, salle);
                  break;
              case "ENSEIGNANT":
              nom =  "ENSEIGNANT :"+ enseignant;
                  cellules = seanceCoursService.getSeancesByJourSemaineAndNomEnseignant(jour, enseignant);
                  break;
              default:
                  System.out.println("Type d'affichage inconnu : " + typeaffichage);
                  break;
          }
          


for (SeanceCours s: cellules){
    seanceManager.ajouterSeanceCours(s);
} 
 System.out.println("--------------- Jour : "+ jour.toString() +" -------------------------------- ");
           // System.out.println("Cellule" + jour.toString() + " ------------------------------------" );
            seanceManager.afficherMap();


            System.out.println("---------------Fin  Jour : "+ jour.toString() +" -------------------------------- ");
           
// appel Service
seanceCoursService.mettreAJourOffsetSelonPositionEtOrdreLigne();

emploiDuTemps.put(jour, seanceManager.getLigneMap());
           // emploiDuTemps.put(jour, seanceManager.getLigneMapAvecMAJOffsetSelonPositionAndOrdreLigne());
        }

    
        model.addAttribute("emploiDuTemps", emploiDuTemps);


        List<String> enseignants = seanceCoursService.getEnseignantsDistincts();   
        List<String> departements = seanceCoursService.getDepartementsDistincts();

        List<String> groupes = seanceCoursService.getGroupesDistincts();

        List<String> salles = seanceCoursService.getSallesDistincts();
        List<String> typeAffichages = Arrays.asList("GROUPE", "SALLE", "ENSEIGNANT");

model.addAttribute("nom", nom);
        model.addAttribute("departements", departements);
        model.addAttribute("groupes", groupes);
        model.addAttribute("salles", salles);
        model.addAttribute("enseignants", enseignants);
        model.addAttribute("typeaffichages", typeAffichages);

        model.addAttribute("currentTypeaffichage", typeaffichage.toString().trim());
model.addAttribute("currentDepartement", departement);
model.addAttribute("currentGroupe", groupe);
model.addAttribute("currentSalle", salle);
model.addAttribute("currentEnseignant", enseignant);
 List<Menu> menus = menuRepository.findAll();
        model.addAttribute("menuList", menus);
        model.addAttribute("currentPageName", "Emplois du Temps");

        return "emplois5";
    }

     // Récupérer les groupes en fonction du département
    @GetMapping("/groupes/{departement}")
    @ResponseBody
    public List<String> getGroupesByDepartement(@PathVariable String departement) {
        if (departement.equals("Tous") || departement.isEmpty()) {
            return seanceCoursService.getGroupesDistincts();  // Retourner tous les groupes
        }
        return seanceCoursService.getDistinctNiveauxByDepartement(departement);
    }

     // Récupérer les matières en fonction du groupe
     @GetMapping("/matieres/{groupe}")
     @ResponseBody
     public List<String> getMatieresByGroupe(@PathVariable String groupe) {
         if (groupe.equals("Tous") || groupe.isEmpty()) {
             // Si le groupe est "Tous" ou vide, retourner toutes les matières distinctes
             return seanceCoursService.getMatieresDistincts();
         }
         // Sinon, retourner les matières spécifiques pour le groupe
         return seanceCoursService.getMatieresByGroupe(groupe);
     }
     



/*  
     @GetMapping("/recherche")
    public String rechercher(
            @RequestParam(required = false) String typeaffichage,
            @RequestParam(required = false) String departement,
            @RequestParam(required = false) String groupe,
            @RequestParam(required = false) String salle,
            @RequestParam(required = false) String enseignant,
            Model model) {

        // Traitement de la recherche avec des valeurs par défaut si nécessaire
        if (typeaffichage == null || typeaffichage.isEmpty()) {
            typeaffichage = "Valeur par défaut";  // ou ce que vous souhaitez
        }
        if (departement == null || departement.isEmpty()) {
            departement = "Tous";  // ou ce que vous souhaitez
        }
        if (groupe == null || groupe.isEmpty()) {
            groupe = "Tous";  // ou ce que vous souhaitez
        }
        if (salle == null || salle.isEmpty()) {
            salle = "";  // Valeur par défaut ou vide
        }
        if (enseignant == null || enseignant.isEmpty()) {
            enseignant = "";  // Valeur par défaut ou vide
        }

        // Exemple de service de recherche
        List<Emploi> resultats = service.rechercher(typeaffichage, departement, groupe, salle, enseignant);

        // Ajouter les résultats à la vue
        model.addAttribute("resultats", resultats);

        return "resultats";  // Vue des résultats
    } */
}
