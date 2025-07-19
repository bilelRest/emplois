package org.iset.emplois.model;

import jakarta.persistence.*;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Nom unique et non null
    private String nomgroupe;

    @Column(length = 500) // Limite de longueur pour la description
    private String description;
 
    @ManyToOne // Relation Many-to-One avec la classe Parcours
    @JoinColumn(name = "parcours_id", nullable = false) // Nom de la clé étrangère
    private Parcours parcours;

    //@Min(1) // Minimum 1
    //@Max(5) // Maximum 5
    @Column(nullable = false) // Champ obligatoire
    private int niveau;

    @Column(nullable = false) // Champ actif obligatoire
    private boolean active;

    @Column(nullable = false) // Vérification Semestre 1 obligatoire
    private boolean verSemestre1;

    @Column(nullable = false) // Vérification Semestre 2 obligatoire
    private boolean verSemestre2;
}
