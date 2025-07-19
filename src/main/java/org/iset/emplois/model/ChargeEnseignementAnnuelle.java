package org.iset.emplois.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChargeEnseignementAnnuelle {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true, length = 300)
    private String nomEC;

    private double prixCoursEnDT;
    private double prixTDEnDT;
    private double prixTPEnDT;
    private double chargeDuEnTDAnnuelle;
    private double conversionCoursEnTD;
    private double conversionTPEnTD;

    
 

    // Relation ManyToOne avec UE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")  // Nom de la colonne clé étrangère
    private Grade grade ;
 
    // Utilisation de @Temporal pour spécifier que c'est une date sans heure
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // Format de date
    private Date datedebut;

    // Utilisation de @Temporal pour spécifier que c'est une date sans heure
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // Format de date
    private Date datefin;
     private boolean actif;
private boolean visible;

@Column (  columnDefinition = "NVARCHAR(255)")
private String description ;

 
}
