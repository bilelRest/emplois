package org.iset.emplois.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient; 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cours") 
public class DetailsGroupeAU {

   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id 
private Long id; 
   private int annee;
 
    private int semestre;
    
    private int numeroLigneTableau;
 

 private String styleBootStrap;
    private boolean autreSeance;
    private boolean par15;
    private boolean typeMatiere;
    @Enumerated(EnumType.STRING)
    private JourSemaine jourSemaine;
    
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
    
     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id") 
    private Enseignant enseignant;

 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id") 
    private Salle salle;

 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creneauhoraire_id") 
    private CreneauHoraire creneau;

 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecue_id") 
    private ECUE ecue;

 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupeau_id") 
    private GroupeAU groupeAU;

 
  
 
    @Transient
    private String nomEnseignant;
    @Transient
    private String nomMatiere;
    @Transient
    private String nomSalle;
    @Transient
    private String nomGroupe;
    @Transient
    private String nomDepartement;
    @Transient
    private String creneauHoraire;


   

    @Transient
    private int niveau;

    public DetailsGroupeAU(Long id, int position, int taille, int numero) {
        this.id = id;
        this.position = position;
        this.taille = taille;
        this.numero = numero;
        this.offset = 0; // Par défaut, initialisé à 0
    }
 






 

}
