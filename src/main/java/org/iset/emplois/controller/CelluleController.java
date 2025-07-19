package org.iset.emplois.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.iset.emplois.model.CarteOptimizer;
import org.iset.emplois.model.Cellule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CelluleController {
@Autowired
    CarteOptimizer carteOptimizer;

    @GetMapping("/cartes")
    public String afficherCartes(Model model) {
        List<Cellule> cartes = List.of(
            new Cellule(1, 4,1),
            new Cellule(2, 6,2),
            new Cellule(3, 2,2),
            new Cellule(4, 8,2),
            new Cellule(5, 6,2),
            new Cellule(6, 4,2),
            new Cellule(7, 2,2)
        );
 
        List<Cellule> cartesOptimisees = carteOptimizer.optimiserDisposition(cartes);

        model.addAttribute("cartes", cartesOptimisees);
        return "carte";
    }



    @GetMapping("/cellules")
public String afficherCellules(Model model) {

    // Liste de cellules avant optimisation
    List<Cellule> cartes = List.of(
        new Cellule(1, 12,0),
        new Cellule(2, 6,2),
        new Cellule(3, 2,4),
        new Cellule(4, 8,2),
        new Cellule(5, 10,2),
        new Cellule(6, 4,4),
        new Cellule(7, 2,4),
        new Cellule(8, 4,0),
        new Cellule(9, 2,0),
        new Cellule(10, 2,2)
    );
System.out.println("Bonjour cartes :"+ cartes.toString());
    // Ajout des cartes avant optimisation au modèle
    model.addAttribute("cellules", cartes);

System.out.println("cellules avant :"+cartes.toString() );
     // Optimisation de la disposition des cartes 
     List<Cellule> cartesOptimisees = carteOptimizer.optimiserDisposition(cartes);
   
     // Regroupement des cellules optimisées par ligne
     System.out.println("cartesOptimisees appel :"+cartesOptimisees.toString() );
 
  // Regroupement des cellules par numéro de ligne
  Map<Integer, List<Cellule>> cellulesOptimisees = cartesOptimisees.stream() 
  .collect(Collectors.groupingBy(Cellule::getNumeroLigne)
 )
  ;
 
     // Ajout des cellules optimisées par ligne au modèle
     model.addAttribute("cellulesOptimisees", cellulesOptimisees);
 // Affichage de chaque clé et des valeurs associées (les cellules)
 System.out.println("Map");
cellulesOptimisees.forEach((key, value) -> {
    System.out.println("Clé (Numéro de ligne): " + key);
    System.out.println("Cellules dans cette ligne: ");
    value.forEach(cellule -> System.out.println("  - " + cellule.toString()));
});
     return "cellules2"; // Correspond au fichier cellules.html
 

 
}
 
}

