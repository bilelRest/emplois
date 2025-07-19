package org.iset.emplois.service;

import java.util.*;
import java.util.stream.Collectors;
import org.iset.emplois.model.SeanceCours;
import org.iset.emplois.repository.SeanceCoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeanceManager {
@Autowired
    private SeanceCoursRepository seanceCoursRepository;
    private Map<Integer, List<SeanceCours>> ligneMap = new HashMap<>();
    private int prochaineLigne = 1;
    public void init(){
        ligneMap.clear(); 
        ligneMap = new HashMap<>();
        prochaineLigne = 1;
    }
    public void ajouterSeanceCours(SeanceCours cellule) {
        boolean ajoute = false;

        for (Map.Entry<Integer, List<SeanceCours>> entry : ligneMap.entrySet()) {
            int ligne = entry.getKey();
            List<SeanceCours> cellules = entry.getValue();

            if (peutAjouterSeanceCours(cellules, cellule)) {
                cellule.setNumero(ligne);
                seanceCoursRepository.save(cellule);
                cellules.add(cellule);
                cellules.sort(Comparator.comparingInt(SeanceCours::getPosition));
                // Mise à jour de l'ordreLigne après le tri
            mettreAJourOrdreLigne(cellules);

                mettreAJourOffset(cellules);
                ajoute = true;
                break;
            }
        }

        if (!ajoute) {
            cellule.setNumero(prochaineLigne);
            List<SeanceCours> nouvelleLigne = new ArrayList<>();
            nouvelleLigne.add(cellule);
            ligneMap.put(prochaineLigne, nouvelleLigne);
            prochaineLigne++;
            
        mettreAJourOrdreLigne(nouvelleLigne);
            mettreAJourOffset(nouvelleLigne);
        }
    }

    private boolean peutAjouterSeanceCours(List<SeanceCours> cellules, SeanceCours cellule) {
        for (SeanceCours c : cellules) {
            if (c.getPosition() == cellule.getPosition()) {
                return false;
            }
            if (cellule.getPosition() < c.getPosition() + c.getTaille() && cellule.getPosition() + cellule.getTaille() > c.getPosition()) {
                return false;
            }
        }
        return true;
    }

    private void mettreAJourOrdreLigne(List<SeanceCours> cellules) {
        for (int i = 0; i < cellules.size(); i++) {
            SeanceCours cellule = cellules.get(i);
          // Garantie que la première cellule dans la ligne a ordreLigne = 1
        cellule.setOrdreLigne(i == 0 ? 1 : i + 1);  // Si c'est la première cellule, ordreLigne = 1, sinon i+1
       
        seanceCoursRepository.save(cellule);  // Enregistrer la mise à jour de ordreLigne
        }
    }

    
    private void mettreAJourOffset(List<SeanceCours> cellules) {
        for (int i = 1; i < cellules.size(); i++) {
            SeanceCours precedente = cellules.get(i - 1);
            SeanceCours courante = cellules.get(i);
            int offset = courante.getPosition() - (precedente.getPosition() + precedente.getTaille());
 /*if ((i==1) && (courante.getPosition()!=0)) courante.setOffset(courante.getPosition());
 else */
            courante.setOffset(offset);
            courante.setOrdreLigne(i + 1);  // Réajuster ordreLigne
            seanceCoursRepository.save(courante);
        }
    }

    public Map<Integer, List<SeanceCours>> getLigneMapAvecMAJOffsetSelonPositionAndOrdreLigne() {
        // Parcourir toutes les lignes
        for (Map.Entry<Integer, List<SeanceCours>> entry : ligneMap.entrySet()) {
            List<SeanceCours> cellules = entry.getValue();
    
            // Parcourir chaque cellule dans la ligne
            for (SeanceCours cellule : cellules) {
                // Condition : OrdreLigne == 1 et Position != 0
                if (cellule.getOrdreLigne() == 1 && cellule.getPosition() != 0) {
                    // Mettre à jour l'offset
                    cellule.setOffset(cellule.getPosition());
    
                    // Sauvegarder la cellule mise à jour
                    seanceCoursRepository.save(cellule);
                }
            }
        }
    
        // Retourner la map mise à jour
        return ligneMap;
    }
    

    public void afficherMap() {
        ligneMap.forEach((ligne, cellules) -> {
            System.out.println("-------------------------- Debut key : " + ligne + "--------------------------------------\n");
            System.out.println("Ligne " + ligne + ": " + cellules);
            System.out.println("---------------------------Fin Key : " + ligne + "--------------------------------\n");
        });
    }

    // Méthode pour récupérer une vue en lecture seule de ligneMap
    public Map<Integer, List<SeanceCours>> getLigneMap() {
        return ligneMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Collections.unmodifiableList(entry.getValue()) // Empêche la modification des listes internes
                ));
    }

    public static void main(String[] args) {
        SeanceManager manager = new SeanceManager();

        SeanceCours c1 = new SeanceCours(1L, 4, 2, 0);
        SeanceCours c2 = new SeanceCours(2L, 2, 3, 0);
        SeanceCours c3 = new SeanceCours(3L, 8, 1, 0);
        SeanceCours c4 = new SeanceCours(4L, 1, 2, 0);

        manager.ajouterSeanceCours(c1);
        manager.ajouterSeanceCours(c2);
        manager.ajouterSeanceCours(c3);
        manager.ajouterSeanceCours(c4);

        manager.afficherMap();
    }
}

