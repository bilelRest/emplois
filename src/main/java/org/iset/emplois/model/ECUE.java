package org.iset.emplois.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ECUE {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true, length = 300)
    private String nomEC;

    private double creditECUE;
    private double coefECUE;
    private double volHTotal;
    private double volHSemaineCours;
    private double volHSemaineTD;
    private double volHSemaineTP;

    

    // Relation ManyToOne avec Matiere
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_id")  // Nom de la colonne clé étrangère
    private Matiere matiere;

    // Relation ManyToOne avec UE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ue_id")  // Nom de la colonne clé étrangère
    private UE ue;

    // Relation ManyToOne avec Parcours
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcours_id")  // Nom de la colonne clé étrangère
    private Parcours parcours;

    // Relation ManyToOne avec PlanEtude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_etude_id")  // Nom de la colonne clé étrangère
    private PlanEtude planEtude;

    private int semestre;
    private int niveau;
    private int ordreECUE;
 
}
