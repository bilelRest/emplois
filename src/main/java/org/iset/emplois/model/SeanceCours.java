package org.iset.emplois.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SeanceCours {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private int annee;
    @Column(nullable = true)
    private int semestre;
  
    private int numeroLigneTableau;
    private String nomEnseignant;
    private String nomMatiere;
    private String nomSalle;
    private String nomGroupe;
    private String nomDepartement;
    private String creneauHoraire;
    private String styleBootStrap;
    private boolean autreSeance;
    private boolean par15;
    private boolean typeMatiere;
    @Enumerated(EnumType.STRING)
    private JourSemaine jourSemaine;
    private Long numsaisie;
    private double nbreHeure;
    @Column(nullable = true)
    private double module;
    @Column(nullable = true)
    private int position;
    @Column(nullable = true)
    private int taille;
    @Column(nullable = true)
    private int numero;
    @Column(nullable = true)
    private int offset;
    @Column(nullable = true)
    private int ordreLigne;

    @Column(nullable = true)
    private double dureeSeance;

    @Column(nullable = true)
    private int niveau;

    public SeanceCours(Long id, int position, int taille, int numero) {
        this.id = id;
        this.position = position;
        this.taille = taille;
        this.numero = numero;
        this.offset = 0; // Par défaut, initialisé à 0
    }
}
