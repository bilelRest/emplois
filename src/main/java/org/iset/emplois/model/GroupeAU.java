package org.iset.emplois.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
 
public class GroupeAU {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 300)
    private String nomgroupe;

    private int nbreEtudiants;
    private int nbreModule;
    private boolean actif;
    private boolean ver;
@Min(1)
    @Max(2)
    private int semestre;


    // Relation ManyToOne avec groupe
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detailsau_id")  // Nom de la colonne clé étrangère
    private DetailsAU detailsAU;



 

   
 
 
}
