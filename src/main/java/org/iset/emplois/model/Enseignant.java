package org.iset.emplois.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enseignant {
 @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id 
private Long id;
 
private String nomenseignant;
 
private String prenomenseignant;

@Column (  columnDefinition = "NVARCHAR(255)")
private  String  prenomenseignantar;
@Column (  columnDefinition = "NVARCHAR(255)")
private  String  nomenseignantar;
@Column (unique = true, nullable = false )
private  String  mail;
@Column (unique = true  )
private  String  tel;

@Column(columnDefinition = "TEXT") // Pour gérer les longues descriptions
private  String  adresse;
private boolean actif;
private boolean visible;

@ManyToOne // Relation Many-to-One avec la classe Parcours
    @JoinColumn(name = "grade_id", nullable = false) // Nom de la clé étrangère
    private Grade grade;

    @ManyToOne // Relation Many-to-One avec la classe Parcours
    @JoinColumn(name = "status_id", nullable = false) // Nom de la clé étrangère
    private StatutEnseignant status;

}

