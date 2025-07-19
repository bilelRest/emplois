package org.iset.emplois.model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class CarteOptimizer {

    private static final int LARGEUR_LIGNE = 12; // Largeur d'une ligne (Bootstrap 12 colonnes)

    public List<Cellule> optimiserDisposition(List<Cellule> cartes) {
        List<Cellule> cartesOptimisees = new ArrayList<>();
        List<Cellule> ligneActuelle = new ArrayList<>();
        
        // Utilisation de AtomicInteger pour permettre de modifier numeroLigne
        AtomicInteger numeroLigne = new AtomicInteger(0); // Remarquez ici que c'est un AtomicInteger

        for (Cellule carte : cartes) {
            boolean ajoutDansLigne = false;

            // Essayer d'ajouter la carte dans la ligne actuelle
            if (ligneActuelle.isEmpty()) {
                carte.setOffset(0);
                carte.setNumeroLigne(numeroLigne.get()); // Utilisation de get() pour récupérer la valeur
                ligneActuelle.add(carte);
                ajoutDansLigne = true;
            } else {
                // Calculer l'espace restant dans la ligne
                int espaceLibre = LARGEUR_LIGNE;
                for (Cellule c : ligneActuelle) {
                    espaceLibre -= c.getTaille(); // Réduire l'espace libre selon la taille des cartes déjà ajoutées
                }

                // Vérifier que la carte peut être ajoutée sans violer la condition de positionnement
                if (espaceLibre >= carte.getTaille()) {
                    int offset = LARGEUR_LIGNE - espaceLibre;

                    // Vérification pour éviter un doublon d'offset dans la même ligne avec le même numéro de ligne
                    boolean positionUnique = ligneActuelle.stream()
                            .noneMatch(c -> c.getOffset() == offset && c.getNumeroLigne() == numeroLigne.get());  // Même position dans la même ligne
                    
                    // Vérification de la règle de "position + taille de la carte précédente"
                    boolean positionValide = true;
                    if (!ligneActuelle.isEmpty()) {
                        // Récupérer la dernière carte de la ligne
                        Cellule derniereCarte = ligneActuelle.get(ligneActuelle.size() - 1);
                        if (derniereCarte.getOffset() + derniereCarte.getTaille() > offset) {
                            positionValide = false; // L'offset de la nouvelle carte est trop petit
                        }
                    }

                    if (positionUnique && positionValide) {
                        carte.setOffset(offset);  // Mettre à jour l'offset de la carte
                        carte.setNumeroLigne(numeroLigne.get());
                        ligneActuelle.add(carte);
                        ajoutDansLigne = true;
                    }
                }
            }

            // Si la carte ne peut pas être ajoutée dans la ligne, on crée une nouvelle ligne
            if (!ajoutDansLigne) {
                // Ajouter la ligne actuelle aux cartes optimisées et réinitialiser la ligne
                cartesOptimisees.addAll(ligneActuelle);
                ligneActuelle.clear();
                numeroLigne.incrementAndGet();  // Incrémenter numeroLigne de manière sûre
                carte.setOffset(0);  // Nouvelle ligne, la carte commence à l'offset 0
                carte.setNumeroLigne(numeroLigne.get());
                ligneActuelle.add(carte);
            }
        }

        // Ajouter la dernière ligne de cartes optimisées si elle existe
        if (!ligneActuelle.isEmpty()) {
            cartesOptimisees.addAll(ligneActuelle);
        }

        return cartesOptimisees;
    }
}
